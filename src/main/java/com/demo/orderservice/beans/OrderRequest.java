package com.demo.orderservice.beans;

import javax.validation.constraints.NotBlank;

public class OrderRequest {

    @NotBlank
    String productId;
    @NotBlank
    String userId;

    ShippingAddress shippingAddress;


    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }
}
