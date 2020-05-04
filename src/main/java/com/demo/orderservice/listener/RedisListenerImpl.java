package com.demo.orderservice.listener;

import com.demo.orderservice.beans.Order;
import com.demo.orderservice.service.OrderService;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * RedisListenerImpl
 */
public class RedisListenerImpl implements MessageListener<Order> {


    private static Logger log = LoggerFactory.getLogger(RedisListenerImpl.class);

    AtomicInteger atm = new AtomicInteger(0);
    @Override
    public void onMessage(String s, Order order) {
        log.info("Count : " + atm.incrementAndGet() + "| Order Received : " + order.toString());
    }
}
