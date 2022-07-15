package com.mhj.ms.acess.control.security.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhj.ms.acess.control.exception.AuthException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler
{
	@Override
	public void handle(final HttpServletRequest request,
					   final HttpServletResponse response,
					   final AccessDeniedException accessDeniedException) throws IOException, ServletException
	{
		final AuthException customException = new AuthException(HttpServletResponse.SC_FORBIDDEN, response.getHeader("retorno"), accessDeniedException) ;
		customException.setMessage(accessDeniedException.getMessage()) ;
		customException.setPath(request.getServletPath()) ;
        
		response.setContentType(MediaType.APPLICATION_JSON_VALUE) ;
        response.setStatus(HttpServletResponse.SC_FORBIDDEN) ;
        
        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), customException.getBodyExceptionMessage()) ;
	}
}
