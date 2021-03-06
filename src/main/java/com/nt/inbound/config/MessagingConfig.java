package com.nt.inbound.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@ConfigurationProperties("backend.messaging")
@Setter
@Profile("default")
@Slf4j
public class MessagingConfig {

    private String manageProductsQueue;
    private String host;
    private String user;
    private String password;
    private String vhost;
    private String url;

    @Bean
    public ConnectionFactory connectionFactory() {
        if (url != null && !url.isEmpty())
            return connectionFactoryWithUri();

        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(host);
        connectionFactory.setUsername(user);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        return connectionFactory;
    }

    private ConnectionFactory connectionFactoryWithUri() {
        try {
            log.info("Create connection factory for '" + url + "'");
            return new CachingConnectionFactory(new URI(url));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Queue manageProductsQueue() {
        Queue queue = new Queue(manageProductsQueue, true);
        rabbitAdmin().declareQueue(queue);
        return queue;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

}
