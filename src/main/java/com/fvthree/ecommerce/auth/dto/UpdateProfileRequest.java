package com.fvthree.ecommerce.auth.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
	
	private Long userId;
	
	private String name;
	
	private String password;
	
	private String address;
}
