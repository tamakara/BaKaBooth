package com.tamakara.booth.backend.service.file;

import com.tamakara.booth.backend.common.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        clients = {},
        defaultConfiguration = FeignConfig.class
)
@MapperScan("com.tamakara.booth.backend.service.file.mapper")
@SpringBootApplication
public class FileServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }
}
