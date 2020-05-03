package com.demo.orderservice.beans;

import java.io.Serializable;

public class Order implements Serializable {

    private String orderId;

    private Product product;

    private User user;

    private ShippingAddress shippingAddress;

    private int port;

    protected Order() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public Order(String orderId, Product product, User user, ShippingAddress shippingAddress, int port) {
        this.orderId = orderId;
        this.product = product;
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.port = port;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", product=" + product +
                ", user=" + user +
                ", shippingAddress=" + shippingAddress +
                ", port=" + port +
                '}';
    }
}
