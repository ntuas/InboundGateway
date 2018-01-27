package com.nt.inbound.services;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(outputDir = "target/snippets")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class InboundServiceControllerTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    public void service_for_putting_a_product_should_have_been_called() throws Exception {

        mockMvc.perform(RestDocumentationRequestBuilders.post("/putProduct/{product}", "Butter"))
                .andExpect(status().isOk())
                .andDo(document("putProduct",
                        pathParameters(parameterWithName("product")
                                .description("The name of the product, which has to be put to the device")
                        )));

        assertThat(outputCapture.toString().contains("Have to put product Butter"));
    }

    @Test
    public void service_for_taking_a_product_should_have_been_called() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.post("/takeProduct/{product}", "Butter"))
                .andExpect(status().isOk())
                .andDo(document("takeProduct",
                        pathParameters(parameterWithName("product")
                                .description("The name of the product, which has to be taken from the device"))));
        assertThat(outputCapture.toString().contains("Have to take product Butter"));
    }

    @Test
    public void service_for_ordering_products_should_have_been_called() throws Exception {
        mockMvc.perform(post("/orderProducts"))
                .andExpect(status().isOk())
                .andDo(document("orderProducts"));
        assertThat(outputCapture.toString().contains("Should order products"));
    }

}