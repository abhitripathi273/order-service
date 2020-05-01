package com.demo.orderservice.beans;

public class ShippingAddress {

    private Integer pinCode;
    private String area;
    private String district;
    private String state;
    private String country;
    private boolean defaultAddress;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    protected ShippingAddress() {

    }

    public ShippingAddress(Integer pinCode, String area, String district, String state, String country, boolean defaultAddress, User user) {
        this.pinCode = pinCode;
        this.area = area;
        this.district = district;
        this.state = state;
        this.country = country;
        this.defaultAddress = defaultAddress;
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShippingAddress{" +
                "pinCode=" + pinCode +
                ", area='" + area + '\'' +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", defaultAddress=" + defaultAddress +
                ", user=" + user +
                '}';
    }
}
