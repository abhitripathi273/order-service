package com.demo.orderservice.feignClients;

import com.demo.orderservice.beans.ShippingAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shipping-address-service", url = "localhost:8600")
public interface ShippingAddressService {

    @PostMapping(path = "/shipping-address-service/user/{userId}/shippingAddress")
    public ResponseEntity<ShippingAddress> createShippingAddress(@PathVariable String userId,
                                                                 @RequestBody ShippingAddress shippingAddress);

}
