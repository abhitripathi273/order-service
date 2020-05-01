package com.demo.orderservice.feignClients;

import com.demo.orderservice.beans.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "localhost:8100")
public interface UserService {

    @GetMapping(path = "/user/{id}")
    public User getUserInfo(@PathVariable("id") String id);

}
