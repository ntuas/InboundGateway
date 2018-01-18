package com.nt.inbound;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class InboundGatewayApplication {

	@Value("${WelcomeMessage:No Welcome Message defined}")
	private String message;

	public static void main(String[] args) {
		SpringApplication.run(InboundGatewayApplication.class, args);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getMessage() {
		return this.message;
	}
}
