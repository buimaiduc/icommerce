package com.icommerce.app.shopping.audit.worker.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableDiscoveryClient
@Configuration
@RefreshScope
public class AppConfiguration {

    @Bean
    public AppShutdownListener appShutdownListener() {
        return new AppShutdownListener();
    }
}
