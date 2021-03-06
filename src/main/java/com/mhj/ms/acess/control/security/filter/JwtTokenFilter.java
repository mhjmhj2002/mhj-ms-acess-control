package com.mhj.ms.acess.control.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.mhj.ms.acess.control.security.JwtTokenProvider;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;

		String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

		log.info("doFilter.Token: {}", token);

		if (token != null) {
			
			if (!jwtTokenProvider.isTokenPresentInDB(token)) {
				response.addHeader("retorno", "Token not found in Database.");
//				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
				filterChain.doFilter(req, res);
				return;
//                throw new CustomException("Invalid JWT token",HttpStatus.UNAUTHORIZED);
			}
			
			try {
				jwtTokenProvider.validateToken(token);
			} catch (JwtException | IllegalArgumentException e) {
				response.addHeader("retorno", "Invalid JWT token.");
//				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
				filterChain.doFilter(req, res);
				return;
//                throw new CustomException("Invalid JWT token",HttpStatus.UNAUTHORIZED);
			}
			
			Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
			// setting auth in the context.
			SecurityContextHolder.getContext().setAuthentication(auth);
			
		}
		
		filterChain.doFilter(req, res);

	}
}