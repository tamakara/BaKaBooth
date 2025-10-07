package com.bakabooth.item;

import com.bakabooth.common.client.FileClient;
import com.bakabooth.common.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        clients = {FileClient.class},
        defaultConfiguration = FeignConfig.class
)
@MapperScan("com.bakabooth.item.mapper")
@SpringBootApplication
public class ItemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemServiceApplication.class, args);
    }
}
