package com.kirisaki;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableApolloConfig
public class DesignprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesignprojectApplication.class, args);
    }

}
