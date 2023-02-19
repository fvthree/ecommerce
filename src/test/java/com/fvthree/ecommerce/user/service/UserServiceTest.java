package com.fvthree.ecommerce.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fvthree.ecommerce.user.model.User;
import com.fvthree.ecommerce.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


@WebMvcTest(UserService.class)
@Slf4j
public class UserServiceTest {
	
	@MockBean
	private UserRepository userRepository;
	
    @MockBean
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	private User user;
	
	@BeforeEach
	public void initialize() {
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
	
	@Test
	public void testUserServiceSaveShouldSuccess() {
		when(userRepository.save(any(User.class))).thenReturn(user);

		
		User savedUser = userService.save(user);
		
		
		assertNotNull(savedUser);
		assertEquals(user.getId(), savedUser.getId());
		assertEquals(user.getName(), savedUser.getName());
		assertEquals(user.getEmail(), savedUser.getEmail());
		assertEquals(user.getRole(), savedUser.getRole());
	}

	
	@Test
	public void testUserServiceUpdateShouldSuccess() {
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
		when(passwordEncoder.encode(any(String.class))).thenReturn("P@s$w0rd!");
		
		User updatedUser = userService.update(1L, user);
		

		log.info(updatedUser.getPassword());
		
		assertNotNull(updatedUser);
		assertEquals(user.getId(), updatedUser.getId());
		assertEquals(user.getName(), updatedUser.getName());
		assertEquals(user.getEmail(), updatedUser.getEmail());
		assertEquals(user.getRole(), updatedUser.getRole());
	}
	
}
