package com.fvthree.ecommerce.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fvthree.ecommerce.user.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	boolean existsByEmail(String email);
	
	boolean existsByName(String name);
	
	Optional<User> findByEmail(String email);
	
	Page<User> findAll(Pageable pageable);
}
