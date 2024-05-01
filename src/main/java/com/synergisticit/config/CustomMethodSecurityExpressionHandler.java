package com.synergisticit.config;

import java.util.Collection;

import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler implements MethodSecurityExpressionHandler {

    protected Authentication getAuthentication() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
    	System.out.println(authorities);
    	return auth;
    }

    @Override
    public CustomPermissionEvaluator getPermissionEvaluator() {
        return new CustomPermissionEvaluator();
    }
}
