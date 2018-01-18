package com.nt.inbound.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Component
@RestController
@Slf4j
public class InboundServiceController {

    @RequestMapping(value = "/putProduct", method = RequestMethod.POST)
    public void putProduct(@RequestParam @NotNull String product) {
        log.info("Have to put product " + product);
    }

    @RequestMapping(value = "/getProduct", method = RequestMethod.POST)
    public void getProduct(@RequestParam @NotNull String product) {
        log.info("Have to get product " + product);
    }

    @RequestMapping(value = "/orderProducts", method = RequestMethod.POST)
    public void getProduct() {
        log.info("Should order products");
    }
}
