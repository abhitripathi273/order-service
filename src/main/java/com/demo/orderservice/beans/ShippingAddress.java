package com.demo.orderservice.beans;

import java.io.Serializable;

public class ShippingAddress implements Serializable {

    private Long userId;
    private Long addressId;
    private Integer pinCode;
    private String area;
    private String district;
    private String state;
    private String country;
    private boolean defaultAddress;

    protected ShippingAddress() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public ShippingAddress(Long userId, Long addressId, Integer pinCode, String area, String district, String state, String country, boolean defaultAddress) {
        this.userId = userId;
        this.addressId = addressId;
        this.pinCode = pinCode;
        this.area = area;
        this.district = district;
        this.state = state;
        this.country = country;
        this.defaultAddress = defaultAddress;
    }

    @Override
    public String toString() {
        return "ShippingAddress{" +
                "userId=" + userId +
                ", addressId=" + addressId +
                ", pinCode=" + pinCode +
                ", area='" + area + '\'' +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", defaultAddress=" + defaultAddress +
                '}';
    }
}
