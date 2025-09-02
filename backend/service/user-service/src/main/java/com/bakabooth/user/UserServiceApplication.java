package com.bakabooth.user;

import com.bakabooth.common.client.ShopClient;
import com.bakabooth.common.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        clients = {ShopClient.class},
        defaultConfiguration = DefaultFeignConfig.class
)
@MapperScan("com.bakabooth.user.mapper")
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
