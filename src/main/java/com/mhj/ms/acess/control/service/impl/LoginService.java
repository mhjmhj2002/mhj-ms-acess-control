package com.mhj.ms.acess.control.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mhj.ms.acess.control.auth.entity.JwtToken;
import com.mhj.ms.acess.control.auth.entity.Role;
import com.mhj.ms.acess.control.auth.entity.User;
import com.mhj.ms.acess.control.exception.CustomException;
import com.mhj.ms.acess.control.repository.JwtTokenRepository;
import com.mhj.ms.acess.control.repository.UserRepository;
import com.mhj.ms.acess.control.security.JwtTokenProvider;
import com.mhj.ms.acess.control.service.ILoginService;

@Service
public class LoginService implements ILoginService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	@Override
	public String login(String username, String password) {

		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (BadCredentialsException | InternalAuthenticationServiceException e) {
			throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
		}
		
		User user = userRepository.findByEmail(username);

		if (user == null || user.getRoles() == null || user.getRoles().isEmpty()) {
			throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
		}
		// NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically
		// for us.
		// Since we are using custom token using JWT we should add ROLE_ prefix
		String token = jwtTokenProvider.createToken(username,
				user.getRoles().stream().map((Role role) -> "ROLE_" + role.getDescricao()).filter(Objects::nonNull)
						.collect(Collectors.toList()));
		return token;

	}

	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public boolean logout(String token) {
		jwtTokenRepository.delete(new JwtToken(token));
		return true;
	}

	@Override
	public Boolean isValidToken(String token) {
		return jwtTokenProvider.validateToken(token);
	}

	@Override
	public String createNewToken(String token) {
		String username = jwtTokenProvider.getUsername(token);
		List<String> roleList = jwtTokenProvider.getRoleList(token);
		String newToken = jwtTokenProvider.createToken(username, roleList);
		return newToken;
	}
}
