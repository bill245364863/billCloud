package com.bill.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.bill.cloud.mapper")
public class BillServiceMain9026 {
    public static void main(String[] args) {
        SpringApplication.run(BillServiceMain9026.class, args);
    }
}
