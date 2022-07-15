package com.mhj.ms.acess.control.security.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhj.ms.acess.control.exception.AuthException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint
{
	@Override
	public void commence(final HttpServletRequest request,
						 final HttpServletResponse response,
						 final AuthenticationException authenticationException) throws IOException, ServletException
	{
		final AuthException authException = new AuthException(HttpServletResponse.SC_UNAUTHORIZED, response.getHeader("retorno"), authenticationException) ;
		authException.setMessage(authenticationException.getMessage()) ;
		authException.setPath(request.getServletPath()) ;
        
		response.setContentType(MediaType.APPLICATION_JSON_VALUE) ;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;
        
        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), authException.getBodyExceptionMessage()) ;
	}
}
