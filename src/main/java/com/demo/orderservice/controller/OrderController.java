package com.demo.orderservice.controller;

import com.demo.orderservice.beans.Order;
import com.demo.orderservice.beans.OrderRequest;
import com.demo.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Order Controller
 */
@RestController
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderService orderService;

    /**
     * create Order
     *
     * @param requestBody
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws JsonProcessingException
     */
    @PostMapping(path = "/create-order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest requestBody) throws InterruptedException, ExecutionException, JsonProcessingException {

        String productId = requestBody.getProductId();
        String userId = requestBody.getUserId();
        Order order = orderService.createOrder(productId, userId);

        return new ResponseEntity(order, HttpStatus.CREATED);
    }

    /**
     * Get Order By Id
     *
     * @param orderId
     * @return
     * @throws IOException
     */
    @GetMapping(path = "/order/{orderId}")
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

        Order order = orderService.getOrder(orderId, data);
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