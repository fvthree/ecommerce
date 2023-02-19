package com.fvthree.ecommerce.auth.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fvthree.ecommerce.user.model.User;

import lombok.Data;

@Data
public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 6114934971303323564L;

	private final Long userId;

    private final String username;

    private final String email;

    private final boolean isVerified;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long userId, String username, String email, boolean isVerified,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.isVerified = isVerified;
		this.password = password;
		this.authorities = authorities;
	}

    public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
			.map(role -> new SimpleGrantedAuthority(role.getName().name()))
			.collect(Collectors.toList());
		
		return new UserDetailsImpl(
					user.getId(),
					user.getName(),
					user.getEmail(),
					user.isVerified(),
					user.getPassword(),
					authorities);
	}
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
    public boolean getIsVerified() {
        return isVerified;
    }

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(userId, user.userId);
    }
}
