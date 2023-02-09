package com.retailer.customer.rewards;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class CustomerRewardsApplication extends SpringBootServletInitializer {

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		log.info("Application Initializing");
		return application.sources(CustomerRewardsApplication.class);
	}
	public static void main(String[] args) {
		log.info("Application Initializing");
		SpringApplication.run(CustomerRewardsApplication.class, args);
	}

	@Bean
	public OpenAPI openApiDoc(@Value("${application-description}") String appDescription,
							  @Value("${application-version}") String appVersion) {
		return new OpenAPI()
				.info(new Info()
						.title("Retailer Rewards Management")
						.version(appVersion)
						.description(appDescription)
						.termsOfService("http://localhost:8080/rewards/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));

	}

}
