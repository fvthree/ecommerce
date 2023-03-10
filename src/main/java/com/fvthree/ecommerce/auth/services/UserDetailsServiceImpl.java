package com.fvthree.ecommerce.auth.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.fvthree.ecommerce.user.model.User;
import com.fvthree.ecommerce.user.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        // although the method says it uses the username, we actually use an email for the same purpose
        User user = userRepository.findByEmail(username)
            .orElseThrow(
                () -> new UsernameNotFoundException(
                    "User Not Found with username/email: " + username));

        return UserDetailsImpl.build(user);
    }
}
