package com.demo.orderservice.beans;

import java.util.List;

public class User {

    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    //private List<ShippingAddress> shippingAddress;
    protected User() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /*public List<ShippingAddress> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(List<ShippingAddress> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }*/

    public User(long userId, String firstName, String lastName, String email, String phoneNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        //this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", shippingAddress=" +                 '}';
    }
}
