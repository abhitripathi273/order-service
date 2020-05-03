package com.demo.orderservice.beans;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderRequest {

    @NotBlank
    String productId;
    @NotBlank
    String userId;

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

}
