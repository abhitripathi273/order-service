package com.demo.orderservice.beans;

public class Order {

    private String orderId;

    private Product product;

    private User user;

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Order(String orderId, Product product, User user, int port) {
        this.orderId = orderId;
        this.product = product;
        this.user = user;
        this.port = port;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", product=" + product +
                ", user=" + user +
                ", port=" + port +
                '}';
    }
}
