package com.fvthree.ecommerce.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fvthree.ecommerce.auth.models.ERole;
import com.fvthree.ecommerce.auth.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}
