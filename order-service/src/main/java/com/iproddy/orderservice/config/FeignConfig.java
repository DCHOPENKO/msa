package com.iproddy.orderservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile("!test")
@EnableFeignClients(basePackages = "com.iproddy.orderservice.http.client")
public class FeignConfig {
}
