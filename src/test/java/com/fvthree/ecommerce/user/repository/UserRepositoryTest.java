 package com.fvthree.ecommerce.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fvthree.ecommerce.user.model.User;

import lombok.extern.slf4j.Slf4j;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	private User user;
	
	private User user2;
	
	@BeforeEach
	public void seedDB() {
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
		user = userRepository.save(user);
		
		user2 = User.builder()
				.id(1L)
				.name("fvthreee")
				.email("felipevillanuevaiiii@gmail.com")
				.address("calamba city")
				.password("123456")
				.isActive(true)
				.isAdmin(false)
				.isNotLocked(false)
				.isVerified(false)
				.role(0)
				.build();
		
		user2 = userRepository.save(user2);
	}
	
	@Test
	public void testShouldReturnExistsByEmail() {
		assertTrue(userRepository.existsByEmail(user.getEmail()));
	}
	
	@Test
    public void testShouldReturnExistsByName() {
        assertTrue(userRepository.existsByName(user.getName()));
    }
	
//	@Test
//	public void testSearchingByKeyword() {
//		List<User> list = userRepository.findUserInNameAndEmailByKeyword("f");
//		assertEquals(list.size(), 2);
//	}
	
	@Test
	public void testShouldReturnElementsFromPageableFindAll() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("dateCreated").descending());
		Page<User> users = userRepository.findAll(pageable);
		log.info(String.valueOf(users.getTotalElements()));
		assertNotNull(users);
		assertTrue(users.getTotalElements() > 0);
		assertEquals(users.getTotalElements(), 2);
	}
}
