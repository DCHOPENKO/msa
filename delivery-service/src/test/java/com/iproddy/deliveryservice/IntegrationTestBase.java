package com.iproddy.deliveryservice;

import com.iproddy.deliveryservice.repository.DeliveryRepository;
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
    public DeliveryRepository deliveryRepository;
    @Autowired
    public ObjectMapper objectMapper;


    @AfterEach
    void cleanup() {
        deliveryRepository.deleteAll();
    }

}
