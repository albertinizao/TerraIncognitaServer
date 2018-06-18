package com.opipo.terraincognitaserver.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.opipo.terraincognitaserver.dto.User;

@Component
public class PermissionEvaluatorImpl implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
            Object permission) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return authentication.getPrincipal().equals(((User) targetDomainObject).getUsername());
    }

}
