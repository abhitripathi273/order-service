package com.demo.orderservice.service;

import com.demo.orderservice.beans.Order;
import com.demo.orderservice.beans.Product;
import com.demo.orderservice.beans.ShippingAddress;
import com.demo.orderservice.beans.User;
import com.demo.orderservice.exception.NotFoundException;
import com.demo.orderservice.listener.RedisListenerImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Order Service
 */
@Service
public class OrderService {

    private static Logger log = LoggerFactory.getLogger(OrderService.class);

    AtomicInteger atm = new AtomicInteger(0);

    @Autowired
    private Environment environment;

    @Autowired
    private AsyncOrderService asyncOrderService;

    /**
     * Create Order
     *
     * @param productId
     * @param userId
     * @param shippingAddress
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws JsonProcessingException
     */
    public Order createOrder(String productId, String userId, ShippingAddress shippingAddress) throws InterruptedException, ExecutionException, JsonProcessingException, NotFoundException {
        // Call Product Microservice if the product exists or not
        //To check if the product its there or not


        CompletableFuture<Product> product = asyncOrderService.getProduct(productId);
        CompletableFuture<User> user = asyncOrderService.getUserInfo(userId);

        CompletableFuture.allOf(user).join();

        if (user.get().getUserId() == null) {
            throw new NotFoundException("User not found");
        }

        CompletableFuture<ResponseEntity<ShippingAddress>> shippingAddressResponse = asyncOrderService.getShippingAddress(userId, shippingAddress);

        CompletableFuture.allOf(product, shippingAddressResponse).join();

        if (product.get().getId() == null) {
            throw new NotFoundException("Product not found");
        }

        int port = Integer.parseInt(environment.getProperty("local.server.port"));
        String orderId = UUID.randomUUID().toString();


        Order order = new Order(orderId, product.get(), user.get(), shippingAddressResponse.get().getBody(), port);

        RedissonClient redissonClient = Redisson.create();
        RTopic topic = redissonClient.getTopic("ORDER-TOPIC");
        /**
         * Publishing to the Topic
         */
        topic.publishAsync(order);
        log.info("Count : " + atm.incrementAndGet() + "| Order Published : " + order.toString());

        //Set the Order in Redis with orderId as the Key
        RBucket<String> bucket = redissonClient.getBucket(orderId);
        ObjectMapper objectMapper = new ObjectMapper();
        bucket.set(objectMapper.writeValueAsString(order));

        /**
         * Adding a Listener
         */
        topic.addListenerAsync(new RedisListenerImpl());

        return order;
    }

    /**
     * Get Order
     *
     * @param orderId
     * @param data
     * @return
     * @throws IOException
     */
    @Cacheable(value = "orderCache", key = "#orderId")
    public Order getOrder(String orderId, String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.readValue(data, Order.class);
        int port = Integer.parseInt(environment.getProperty("local.server.port"));
        order.setPort(port);
        return order;
    }

}
