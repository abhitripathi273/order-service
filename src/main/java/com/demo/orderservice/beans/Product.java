package com.demo.orderservice.beans;

public class Product {

    String productID;

    protected Product() {

    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Product(String productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID='" + productID + '\'' +
                '}';
    }
}
