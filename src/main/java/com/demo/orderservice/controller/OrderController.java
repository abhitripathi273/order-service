package com.demo.orderservice.controller;

import com.demo.orderservice.beans.Order;
import com.demo.orderservice.beans.Product;
import com.demo.orderservice.beans.User;
import com.demo.orderservice.service.AsyncOrderService;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private Environment environment;

    @Autowired
    private AsyncOrderService asyncOrderService;

    @PostMapping(path = "/shop/order")
    @ResponseStatus
    public ResponseEntity<Order> createOrder(@RequestBody JsonNode requestBody) throws InterruptedException, ExecutionException, JsonProcessingException {

        String productId = requestBody.get("productId").asText();
        String userId = requestBody.get("userId").asText();

        // Call Product Microservice if the product exists or not

        //To check if the product its there or not

       /* CompletableFuture<Product> product = asyncOrderService.getProduct(productId);

        CompletableFuture<User> user = asyncOrderService.getUserInfo(userId);
*/

        Product product = new Product(productId);
        User user = new User(userId, "address123");


        /* CompletableFuture.allOf(product, user).join();
         */
        int port = Integer.parseInt(environment.getProperty("local.server.port"));

        //Set the Order in Redis with orderId as the Key

        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, product, user, port);
        //Order order = new Order(orderId, product.get(), user.get(), port);
        RedissonClient redissonClient = Redisson.create();

        RBucket<String> bucket = redissonClient.getBucket(orderId);
        ObjectMapper objectMapper = new ObjectMapper();
        bucket.set(objectMapper.writeValueAsString(order));

        return new ResponseEntity(order, HttpStatus.CREATED);
    }

    @GetMapping(path = "/shop/order/{orderId}")
    public ResponseEntity<Order> getOrderId(@PathVariable String orderId) throws IOException {

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
        JsonNode node = objectMapper.readTree(data);
        int port = Integer.parseInt(environment.getProperty("local.server.port"));

        return new ResponseEntity(node, HttpStatus.OK);
    }

    private JsonNode getErrorMessageNode() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String errorMessage = "{ \"message\":\"Order not found\" }";
        return objectMapper.readTree(errorMessage);
    }
}