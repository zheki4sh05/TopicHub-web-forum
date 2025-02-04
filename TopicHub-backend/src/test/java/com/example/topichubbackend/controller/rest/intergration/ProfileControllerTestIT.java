package com.example.topichubbackend.controller.rest.intergration;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.controller.rest.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.services.impls.*;
import jakarta.validation.*;
import lombok.*;
import org.hibernate.validator.messageinterpolation.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.validation.beanvalidation.*;
import org.springframework.web.context.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//@ContextConfiguration(classes = {PersistenceConfigurationTest.class,
//        ProfileController.class, ArticleService.class, ImageService.class, AuthService.class})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfigurationTest.class, SpringConfig.class, ValidatorConfig.class})
//@ActiveProfiles("test")
@WebAppConfiguration
public class ProfileControllerTestIT {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    @SneakyThrows
    void testDoGet(){

        this.mockMvc.perform(get("/api/v1/profile")
                .param("page", "1")
                .param("type", "articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }



}
