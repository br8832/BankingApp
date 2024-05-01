package com.synergisticit.config;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        System.out.println("in here customer");
    	if ("hasRole".equals((String)permission)) {
        	System.out.println((String)permission);
            String roleName = (String) targetDomainObject;
            User user = (User) authentication.getPrincipal();
            return user.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(roleName));
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException("Method hasPermission() is not supported");
    }
}
