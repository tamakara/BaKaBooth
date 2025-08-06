package com.bakabooth.shop;

import com.bakabooth.common.client.UserClient;
import com.bakabooth.common.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        clients = {UserClient.class},
        defaultConfiguration = DefaultFeignConfig.class
)
@MapperScan("com.bakabooth.shop.mapper")
@SpringBootApplication
public class ShopServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopServiceApplication.class, args);
    }
}
