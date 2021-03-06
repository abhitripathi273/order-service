package com.demo.orderservice.controller;

import com.demo.orderservice.beans.Order;
import com.demo.orderservice.beans.OrderRequest;
import com.demo.orderservice.beans.ShippingAddress;
import com.demo.orderservice.exception.NotFoundException;
import com.demo.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest requestBody) throws InterruptedException, ExecutionException, JsonProcessingException, NotFoundException {


        String productId = requestBody.getProductId();
        String userId = requestBody.getUserId();
        ShippingAddress shippingAddress = requestBody.getShippingAddress();

        Order order = orderService.createOrder(productId, userId, shippingAddress);

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
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) throws IOException, NotFoundException {

        Order order = orderService.getOrder(orderId);

        return new ResponseEntity(order, HttpStatus.OK);
    }

}