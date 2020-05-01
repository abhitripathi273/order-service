package com.demo.orderservice.service;

import com.demo.orderservice.beans.Order;
import com.demo.orderservice.beans.Product;
import com.demo.orderservice.beans.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
        CompletableFuture<Product> product = asyncOrderService.getProduct(productId);
        CompletableFuture<User> user = asyncOrderService.getUserInfo(userId);

        CompletableFuture.allOf(product, user).join();

        int port = Integer.parseInt(environment.getProperty("local.server.port"));
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, product.get(), user.get(), port);
        //Set the Order in Redis with orderId as the Key
        RedissonClient redissonClient = Redisson.create();

        RBucket<String> bucket = redissonClient.getBucket(orderId);
        ObjectMapper objectMapper = new ObjectMapper();
        bucket.set(objectMapper.writeValueAsString(order));
        return order;
    }

    /**
     * Get Order
     *
     * @param orderId
     * @return
     * @throws IOException
     */
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) throws IOException {
        RedissonClient redissonClient = Redisson.create();
        //fetch order from Redis , as OrderId would be the key in Redis
        RBucket<String> dataBucket = redissonClient.getBucket(orderId);
        String data = null;
        if (null != dataBucket) {
            data = dataBucket.get();
            if (null == data) {
                log.info("No data found for key {}.", orderId);
                return new ResponseEntity(getErrorMessageNode(), HttpStatus.NOT_FOUND);
            }
        } else {
            log.warn("No data found for key {}.", orderId);
            return new ResponseEntity(getErrorMessageNode(), HttpStatus.NOT_FOUND);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.readValue(data, Order.class);
        int port = Integer.parseInt(environment.getProperty("local.server.port"));
        order.setPort(port);
        return new ResponseEntity(order, HttpStatus.OK);
    }


    /**
     * Get Error MessageNode
     *
     * @return
     * @throws IOException
     */
    private JsonNode getErrorMessageNode() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String errorMessage = "{ \"message\":\"Order not found\" }";
        return objectMapper.readTree(errorMessage);
    }
}
