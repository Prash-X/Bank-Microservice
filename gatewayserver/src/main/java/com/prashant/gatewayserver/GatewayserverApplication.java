package com.prashant.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator madhouseBankRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/madhousebank/accounts/**")
						.filters(f->f.rewritePath("/madhousebank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/madhousebank/loans/**")
						.filters(f->f.rewritePath("/madhousebank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/madhousebank/cards/**")
						.filters(f->f.rewritePath("/madhousebank/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS")).build();
	}
}
