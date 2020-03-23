package com.security.app.auth;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.app.auth.domain.AuthGroup;
import com.security.app.auth.domain.User;
import com.security.app.auth.repository.AuthGroupRepository;
import com.security.app.auth.repository.UserRepository;

@Service
public class LandonUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	private final AuthGroupRepository authGroupRepository;
	
	
	public LandonUserDetailsService(UserRepository userRepository, AuthGroupRepository authGroupRepository) {
		this.userRepository = userRepository;
		this.authGroupRepository = authGroupRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("cannot find username: " + username);
		}
		
		List<AuthGroup> authGroups = authGroupRepository.findByUsername(username);
		
		return new LandonUserPrincipal(user, authGroups);
	}
}
