package com.demo.orderservice.beans;

public class User {

    private String userId;

    private String shippingAddress;

    protected User() {

    }

    public String getUserId() {
        return userId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public User(String userId, String shippingAddress) {
        this.userId = userId;
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }
}
