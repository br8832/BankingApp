package com.synergisticit.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
	
    @Autowired
    private SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        //savedRequestAwareAuthenticationSuccessHandler.setDefaultTargetUrl(request.getRequestURI());
        //savedRequestAwareAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        savedRequestAwareAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }
}