package com.demo.orderservice.controller;

import com.demo.orderservice.beans.Order;
import com.demo.orderservice.beans.OrderRequest;
import com.demo.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        ResponseEntity<Order> node = orderService.getOrder(orderId);
        return node;
    }

}