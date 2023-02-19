package com.fvthree.ecommerce.auth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fvthree.ecommerce.auth.dto.JwtResponse;
import com.fvthree.ecommerce.auth.jwt.JwtUtils;
import com.fvthree.ecommerce.user.model.User;
import com.fvthree.ecommerce.user.repository.UserRepository;

import exceptions.HTTP404Exception;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AuthService {
	
	@Autowired
	private final AuthenticationManager authenticationManager;
    
	@Autowired
	private final UserRepository userRepository;
    
	@Autowired
	private final PasswordEncoder encoder;
    
	@Autowired
	private final JwtUtils jwtUtils;
	
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
			PasswordEncoder encoder, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	public JwtResponse authenticateUser(final String email, final String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String jwtRefresh = jwtUtils.generateJwtRefreshToken(authentication);

        jwtUtils.whitelistJwtPair(jwt, jwtRefresh);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return new JwtResponse(jwt,
	            jwtRefresh,
	            userDetails.getUserId(),
	            userDetails.getEmail(),
	            userDetails.getIsVerified(),
	            roles.get(0));
    }

    public User getCurrentlyAuthenticatedUser() {
    	UserDetailsImpl userDetails =
            (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new HTTP404Exception("User not found"));
    }

    public String encodePassword(String password) {
        return encoder.encode(password);
    }
}
