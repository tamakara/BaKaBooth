package com.bakabooth.shop;

import com.bakabooth.client.UserClient;
import com.bakabooth.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        clients = {UserClient.class},
        defaultConfiguration = DefaultFeignConfig.class
)
@SpringBootApplication
public class ShopServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopServiceApplication.class, args);
    }
}