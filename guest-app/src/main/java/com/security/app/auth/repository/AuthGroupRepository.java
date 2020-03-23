package com.security.app.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.app.auth.domain.AuthGroup;

@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Long> {
	List<AuthGroup> findByUsername(String username);
}
