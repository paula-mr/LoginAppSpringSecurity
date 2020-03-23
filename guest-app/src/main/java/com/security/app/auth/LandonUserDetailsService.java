package com.security.app.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.app.auth.domain.User;
import com.security.app.auth.repository.UserRepository;

@Service
public class LandonUserDetailsService implements UserDetailsService {
	
	private final UserRepository repository;
	
	public LandonUserDetailsService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("cannot find username: " + username);
		}
		
		return new LandonUserPrincipal(user);
	}
}
