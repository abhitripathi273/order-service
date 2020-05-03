package com.demo.orderservice.feignClients;

import com.demo.orderservice.beans.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "localhost:8600")
public interface ProductService {

    @GetMapping(path = "/product-service/shop/products/{id}")
    public Product getProduct(@PathVariable("id") String id);

}
