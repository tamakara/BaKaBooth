package com.bakabooth.user;

import com.bakabooth.client.ShopClient;
import com.bakabooth.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        clients = {ShopClient.class},
        defaultConfiguration = DefaultFeignConfig.class
)
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
