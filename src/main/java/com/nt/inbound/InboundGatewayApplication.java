package com.nt.inbound;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class InboundGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(InboundGatewayApplication.class, args);
	}

	@PostConstruct
	public void logHostIp() throws UnknownHostException {
		log.info("Host Ip Address: {}", InetAddress.getLocalHost().getHostAddress());
	}
}
