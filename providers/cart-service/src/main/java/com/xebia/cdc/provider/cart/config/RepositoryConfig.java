package com.xebia.cdc.provider.cart.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.xebia.cdc.provider.cart")
@EnableAutoConfiguration
@EntityScan(basePackages = "com.xebia.cdc.provider.cart")
public class RepositoryConfig {
}
