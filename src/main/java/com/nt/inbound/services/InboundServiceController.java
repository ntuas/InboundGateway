package com.nt.inbound.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@RestController
@Slf4j
public class InboundServiceController {

    @Autowired
    private Queue manageProductsQueue;

    @Autowired
    private Queue inboundGatewayReplyTo;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping(value = "/putProduct/{product}", method = RequestMethod.POST)
    public void putProduct(@PathVariable("product") String product) {
        log.info("Have to put product " + product);
        informBackendToActForProduct("put", product);
    }

    @RequestMapping(value = {"/count", "/count/{product}"}, method = RequestMethod.GET)
    public List<Map<String, Integer>> getAmountOfAvailableProducts(@PathVariable(required = false, value = "product") Optional<String> product) throws InterruptedException, ExecutionException, TimeoutException {
        log.info("Have to count {}", product.isPresent() ? " available amount of " + product.get() : " available amount of all products");

        Message messageToSend = MessageBuilder.withBody(product.isPresent() ? product.get().getBytes() : "".getBytes())
                .andProperties(
                        MessagePropertiesBuilder.newInstance()
                                .setHeader("action", "count")
                                .build())
                .build();

        Message returnedMessage = rabbitTemplate.sendAndReceive(manageProductsQueue.getName(),messageToSend);
        log.info("Received response {}", new String(returnedMessage.getBody()));

        return new ArrayList<>();
    }

    @RequestMapping(value = "/takeProduct/{product}", method = RequestMethod.POST)
    public void takeProduct(@PathVariable("product") String product) {
        log.info("Have to take product " + product);
        informBackendToActForProduct("take", product);
    }

    @RequestMapping(value = "/orderProducts", method = RequestMethod.POST)
    public void orderProduct() {
        log.info("Should order products");
        informBackendToActForProduct("order", "");
    }

    private void informBackendToActForProduct(String action, String body) {
        rabbitTemplate.send(manageProductsQueue.getName(), createMessageToSend(action, body));
    }

    private Message createMessageToSend(String action, String body) {
        return MessageBuilder.withBody(body.getBytes())
                .andProperties(
                        MessagePropertiesBuilder.newInstance().setHeader("action", action).build())
                .build();
    }
}
