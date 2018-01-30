package com.nt.inbound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class InboundGatewayApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(InboundGatewayApplication.class);

	@Value("${WelcomeMessage:No Welcome Message defined}")
	private String message;

	public static void main(String[] args) {
		SpringApplication.run(InboundGatewayApplication.class, args);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getMessage() {
		return this.message;
	}

	@PostConstruct
	public void logHostIp() throws UnknownHostException {
		LOGGER.info("Host Ip Address: {}", InetAddress.getLocalHost().getHostAddress());
	}
}
