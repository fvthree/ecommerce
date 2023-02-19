package com.fvthree.ecommerce.auth.dto;

import lombok.Data;

@Data
public class JwtResponse {

	private final String roles;

	private String accessToken;

	private String refreshToken;

	private String tokenType = "Bearer";

	private Long userId;

	private String email;

	private boolean isVerified;

	public JwtResponse(String accessToken, String refreshToken, Long userId, String email, boolean isVerified,
			String roles) {
		this.refreshToken = refreshToken;
		this.accessToken = accessToken;
		this.userId = userId;
		this.email = email;
		this.roles = roles;
		this.isVerified = isVerified;
	}
}
