package com.fvthree.ecommerce.user.service;

import org.springframework.stereotype.Service;
import com.fvthree.ecommerce.user.model.User;

import dto.UsersPagedResponse;

@Service
public interface UserService {
	User save(User user);
	User update(long id, User user);
    UsersPagedResponse getUsersByPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
