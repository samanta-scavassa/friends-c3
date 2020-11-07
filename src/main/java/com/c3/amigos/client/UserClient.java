package com.c3.amigos.client;

import com.c3.amigos.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("user")
public interface UserClient {

    @GetMapping("/api/v1/user/{id}")
    UserDTO getUserById(@RequestHeader("Authorization") String bearerToken, @PathVariable Long id);

}
