package com.nt.inbound.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@Slf4j
public class InboundServiceController {

    @Autowired
    private Queue manageProductsQueue;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/putProduct/{product}", method = RequestMethod.POST)
    public void putProduct(@PathVariable("product") String product) {
        log.info("Have to put product " + product);
        informBackendToActForProduct("put", product);
    }

    @RequestMapping(value = "/getProduct/{product}", method = RequestMethod.POST)
    public void getProduct(@PathVariable("product") String product) {
        log.info("Have to get product " + product);
        informBackendToActForProduct("get", product);
    }

    @RequestMapping(value = "/orderProducts", method = RequestMethod.POST)
    public void getProduct() {
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
