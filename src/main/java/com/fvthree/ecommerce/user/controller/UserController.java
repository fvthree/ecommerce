package com.fvthree.ecommerce.user.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fvthree.ecommerce.auth.dto.UpdateProfileRequest;
import com.fvthree.ecommerce.user.model.User;
import com.fvthree.ecommerce.user.service.UserService;

import dto.UsersPagedResponse;
import events.UserEvent;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController extends AbstractController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        UserEvent userCreatedEvent = new UserEvent("One User is created", savedUser);
        eventPublisher.publishEvent(userCreatedEvent);
        return ResponseEntity.ok().body("New User has been saved with ID:" + savedUser.getId());
    }
    
    @GetMapping("/user")
    public @ResponseBody UsersPagedResponse getUsersByPage(
            @RequestParam(value="pagenumber", required=true, defaultValue="0") Integer pageNumber,
            @RequestParam(value="pagesize", required=true, defaultValue="20") Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "dateCreated", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        return userService.getUsersByPage(pageNumber, pageSize, sortBy, sortDir);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid UpdateProfileRequest request) {
    	User updateUser = User.builder()
    			.id(request.getUserId())
    			.name(request.getName())
    			.password(request.getPassword())
    			.address(request.getAddress())
    			.build();
    	userService.update(request.getUserId(), updateUser);
    	UserEvent userUpdatedEvent = new UserEvent("One User is updated", updateUser);
        eventPublisher.publishEvent(userUpdatedEvent);
        return ResponseEntity.ok().body("User has been updated successfully.");
    }
	
}
