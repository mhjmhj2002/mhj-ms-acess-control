package com.mhj.ms.acess.control.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.mhj.ms.acess.control.auth.entity.JwtToken;
import com.mhj.ms.acess.control.dto.response.UserDetailsDto;
import com.mhj.ms.acess.control.repository.JwtTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	private static final String AUTH = "auth";
	private static final String AUTHORIZATION = "Authorization";
	private String secretKey = "secret-key";
	private static long SECOND_IN_MILIS = 1000;
	private static long MINUTE_IN_MILIS = 60 * SECOND_IN_MILIS;
//	private static long HOUR_IN_MILIS = 60 * MINUTE_IN_MILIS;

	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String username, List<String> roles) {

		log.info("createToken.username: {}", username);
		log.info("createToken.roles: {}", roles);

		Claims claims = Jwts.claims().setSubject(username);
		claims.put(AUTH, roles);

		Date now = new Date();
		Date validity = new Date(now.getTime() + MINUTE_IN_MILIS);// HOUR_IN_MILIS);

		String token = Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
		jwtTokenRepository.save(new JwtToken(token));
		return token;
	}

	public String resolveToken(HttpServletRequest req) {
		
		String bearerToken = req.getHeader(AUTHORIZATION);
		
		log.info("resolveToken.bearerToken: {}", bearerToken);

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}

		if (bearerToken != null) {
			return bearerToken;
		}
		return null;
	}

	public boolean validateToken(String token) throws JwtException, IllegalArgumentException {
		
		log.info("validateToken.token: {}", token);
		
		Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		return true;
	}

	public boolean isTokenPresentInDB(String token) {
		
		log.info("isTokenPresentInDB.token: {}", token);
		
		return jwtTokenRepository.findById(token).isPresent();
	}

	// user details with out database hit
	public UserDetails getUserDetails(String token) {
		
		log.info("getUserDetails.token: {}", token);
		
		String userName = getUsername(token);
		List<String> roleList = getRoleList(token);
		UserDetails userDetails = new UserDetailsDto(userName, roleList.toArray(new String[roleList.size()]));
		return userDetails;
	}

	public List<String> getRoleList(String token) {
		
		log.info("getRoleList.token: {}", token);
		
		return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(AUTH);
	}

	public String getUsername(String token) {
		
		log.info("getUsername.token: {}", token);
		
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public Authentication getAuthentication(String token) {
		
		log.info("getAuthentication.token: {}", token);
		
		// using data base: uncomment when you want to fetch data from data base
		// UserDetails userDetails =
		// userDetailsService.loadUserByUsername(getUsername(token));
		// from token take user value. comment below line for changing it taking from
		// data base
		UserDetails userDetails = getUserDetails(token);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

}