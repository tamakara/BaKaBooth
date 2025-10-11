package com.tamakara.booth.backend.service.item;

import com.tamakara.booth.backend.common.client.FileClient;
import com.tamakara.booth.backend.common.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        clients = {FileClient.class},
        defaultConfiguration = FeignConfig.class
)
@MapperScan("com.tamakara.booth.backend.service.item.mapper")
@SpringBootApplication
public class ItemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemServiceApplication.class, args);
    }
}
