package com.leadgen.backend.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.leadgen.backend.repository")
public class JpaConfig {
}
