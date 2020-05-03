package com.demo.orderservice.service;

import com.demo.orderservice.beans.Order;
import com.demo.orderservice.beans.Product;
import com.demo.orderservice.beans.User;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Order Service
 */
@Service
public class OrderService {

    private static Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private Environment environment;

    @Autowired
    private AsyncOrderService asyncOrderService;

    /**
     * Create Order
     *
     * @param productId
     * @param userId
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws JsonProcessingException
     */
    public Order createOrder(String productId, String userId) throws InterruptedException, ExecutionException, JsonProcessingException {
        // Call Product Microservice if the product exists or not
        //To check if the product its there or not

        /**
         * Adding a Listener
         */
        RedissonClient redissonClient = Redisson.create();
        RTopic topic2 = redissonClient.getTopic("ORDERTOPIC");
        topic2.addListener(new RedisListenerImpl());


        CompletableFuture<Product> product = asyncOrderService.getProduct(productId);
        CompletableFuture<User> user = asyncOrderService.getUserInfo(userId);

        CompletableFuture.allOf(product, user).join();

        int port = Integer.parseInt(environment.getProperty("local.server.port"));
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, product.get(), user.get(), port);

        //Set the Order in Redis with orderId as the Key
        RBucket<String> bucket = redissonClient.getBucket(orderId);
        ObjectMapper objectMapper = new ObjectMapper();
        bucket.set(objectMapper.writeValueAsString(order));


        /**
         * Publishing to the Topic
         */
        RTopic topic = redissonClient.getTopic("ORDERTOPIC");
        topic.publish(order);

        //redissonClient.shutdown();
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
