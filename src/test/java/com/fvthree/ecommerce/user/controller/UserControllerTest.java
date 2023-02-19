package com.fvthree.ecommerce.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fvthree.ecommerce.config.UserMetricsConfiguration;
import com.fvthree.ecommerce.user.model.User;
import com.fvthree.ecommerce.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@ComponentScan
@WebMvcTest(value = UserController.class)
@Import({MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class, UserMetricsConfiguration.class})
@Slf4j
public class UserControllerTest {
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private User user;
	
	@BeforeEach
	void initialize() {
		user = User.builder()
				.id(1L)
				.name("fvthree")
				.email("felipevillanuevaiii@gmail.com")
				.address("calamba city")
				.password("123456")
				.isActive(true)
				.isAdmin(false)
				.isNotLocked(false)
				.isVerified(false)
				.role(0)
				.build();
	}
	
//	@Test
//	void testCreateUser() throws Exception {
//		
//		String requestContent = new ObjectMapper().writeValueAsString(user);
//		
//		RequestBuilder request = MockMvcRequestBuilders
//				.post("/user")
//				.content(requestContent)
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.accept(MediaType.APPLICATION_JSON);
//		
//		when(userService.save(any(User.class))).thenReturn(user);
//		
//		MvcResult mvcResult = mockMvc.perform(request)
//				.andExpect(status().is2xxSuccessful())
//                .andReturn();
//		
//        log.info(mvcResult.getResponse().getContentAsString());
//	}
}
