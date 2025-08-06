package com.bakabooth.common.client;

import com.bakabooth.common.domain.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service")
public interface UserClient {
    @GetMapping("/dto/user")
    ResponseEntity<UserDTO> getUserDTO(@RequestParam Long userId);
}
