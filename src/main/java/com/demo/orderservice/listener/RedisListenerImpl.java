package com.demo.orderservice.listener;

import com.demo.orderservice.beans.Order;
import org.redisson.api.listener.MessageListener;

/**
 * RedisListenerImpl
 */
public class RedisListenerImpl implements MessageListener<Order> {
    @Override
    public void onMessage(String s, Order order) {
        System.out.println("Order Received: " + order.toString());
    }
}
