package com.nt.twelvefactorapp.InboundGateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class InboundGatewayApplication {

	@Value("${WelcomeMessage:No Welcome Message defined}")
	private String message;

	public static void main(String[] args) {
		SpringApplication.run(InboundGatewayApplication.class, args);
	}

	@RequestMapping("/")
	public String getMessage() {
		return this.message;
	}
}
