package com.jeovan.trainingmicroservice.controllers;

import com.jeovan.trainingmicroservice.security.SecurityConfig;
import com.jeovan.trainingmicroservice.security.jwt.JwtAuthenticationFilter;
import com.jeovan.trainingmicroservice.security.jwt.JwtDecoder;
import com.jeovan.trainingmicroservice.services.TrainerScheduleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainerScheduleController.class)
@ContextConfiguration(classes = {TrainerScheduleController.class, JwtDecoder.class, SecurityConfig.class, JwtAuthenticationFilter.class})
class TrainerScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainerScheduleService trainerScheduleService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithMockUser
    public void testHello() throws Exception {
    }
}