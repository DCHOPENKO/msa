package com.iproddy.paymentservice;

import com.iproddy.paymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationTestBase {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public PaymentRepository paymentRepository;
    @Autowired
    public ObjectMapper objectMapper;


    @AfterEach
    void cleanup() {
        paymentRepository.deleteAll();
    }

}
