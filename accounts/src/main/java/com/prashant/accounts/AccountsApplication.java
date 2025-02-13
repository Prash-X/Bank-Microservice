package com.prashant.accounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title="Accounts microservice REST API Documentation",
				description = "Prashant Accounts Services",
				version = "v1.0"
		)
)
@EnableFeignClients
public class AccountsApplication {


	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
