package com.fvthree.ecommerce.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fvthree.ecommerce.user.model.User;
import com.fvthree.ecommerce.user.repository.UserRepository;

import dto.UsersPagedResponse;
import exceptions.HTTP400Exception;
import exceptions.HTTP404Exception;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
    private final PasswordEncoder encoder;
	
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	@Override
	public User save(User user) {
		try {
			if (userRepository.existsByName(user.getName()))
				throw new HTTP400Exception("name already exists");
			if (userRepository.existsByEmail(user.getEmail()))
				throw new HTTP400Exception("email already exists");
			return userRepository.save(user);
		} catch(Exception e) {
			throw new HTTP400Exception(e.getMessage());
		}
	}

	@Override
	public User update(long id, User user) {
		User savedUser = userRepository.findById(id)
				.orElseThrow(() -> new HTTP404Exception("User not found."));

		validateUpdateUser(savedUser, user);
		
		savedUser.setName(user.getName());
		savedUser.setPassword(encoder.encode(user.getPassword()));
		savedUser.setAddress(user.getAddress());
		
		return userRepository.save(savedUser);
	}
	
    @Override
    public  UsersPagedResponse getUsersByPage(Integer pageNumber, Integer pageSize, String  sortBy, String sortDir) {
    	
    	Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    	
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        
        Page<User> pagedUsers = userRepository.findAll(pageable);
        
        List<User> listOfUsers = pagedUsers.getContent();
        
        UsersPagedResponse userPagedResponse = UsersPagedResponse.builder()
				.content(listOfUsers)
				.pageNo(pagedUsers.getNumber())
				.pageSize(pagedUsers.getSize())
				.totalElements(pagedUsers.getTotalElements())
				.totalPages(pagedUsers.getTotalPages())
				.last(pagedUsers.isLast())
				.build();
        
        return userPagedResponse;
    }
    
    private void validateUpdateUser(User savedUser, User newUser) {
		if (!newUser.getName().equals(savedUser.getName())) {
			if (userRepository.existsByName(newUser.getName())) {
				throw new HTTP400Exception("name already exists.");
			}
		}
    }
}
