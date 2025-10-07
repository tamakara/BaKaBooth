package com.bakabooth.order;

import com.bakabooth.common.client.ItemClient;
import com.bakabooth.common.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        clients = {ItemClient.class},
        defaultConfiguration = FeignConfig.class
)
@MapperScan("com.bakabooth.order.mapper")
@SpringBootApplication
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
