package com.demo.orderservice.service;

import com.demo.orderservice.beans.Product;
import com.demo.orderservice.beans.ShippingAddress;
import com.demo.orderservice.beans.User;
import com.demo.orderservice.feignClients.ProductService;
import com.demo.orderservice.feignClients.ShippingAddressService;
import com.demo.orderservice.feignClients.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.CompletableFuture;

/**
 * Async OrderService
 * Used to call Feign Clients in async way
 */
@Service
public class AsyncOrderService {

    private static Logger log = LoggerFactory.getLogger(AsyncOrderService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Async("asyncExecutor")
    public CompletableFuture<User> getUserInfo(String id) throws InterruptedException {

        log.info("getUserInfo starts");
        User user = userService.getUserInfo(id);
        log.info("user, {}", user);

        Thread.sleep(1000L);    //Intentional delay
        log.info("getUserInfo completed");
        return CompletableFuture.completedFuture(user);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Product> getProduct(String id) throws InterruptedException {

        log.info("getProduct starts");
        Product product = productService.getProduct(id);
        log.info("product, {}", product);

        Thread.sleep(1000L);    //Intentional delay
        log.info("getProduct completed");
        return CompletableFuture.completedFuture(product);
    }

    @Async("asyncExecutor")
    public CompletableFuture<ResponseEntity<ShippingAddress>> getShippingAddress(String userId,
                                                                         ShippingAddress shippingAddress) throws InterruptedException {

        log.info("getShippingAddress starts");
        ResponseEntity<ShippingAddress> responseShippingAddress = shippingAddressService.createShippingAddress(userId,shippingAddress);
        log.info("ShippingAddress, {}", responseShippingAddress);

        Thread.sleep(1000L);    //Intentional delay
        log.info("getShippingAddress completed");
        return CompletableFuture.completedFuture(responseShippingAddress);
    }
}
