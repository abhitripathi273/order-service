package com.demo.orderservice.listener;

import com.demo.orderservice.beans.Order;
import com.demo.orderservice.service.OrderService;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RedisListenerImpl
 */
public class RedisListenerImpl implements MessageListener<Order> {

    private static Logger log = LoggerFactory.getLogger(OrderService.class);

    @Override
    public void onMessage(String s, Order order) {

        log.info("Order Received: " + order.toString());

    }
}
