package com.opipo.terraincognitaserver.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.opipo.terraincognitaserver.dto.Owneable;

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
        Object id = Owneable.class.isAssignableFrom(targetDomainObject.getClass())
                ? ((Owneable) targetDomainObject).getOwner()
                : targetDomainObject;
        return authentication.getPrincipal().equals(id);
    }

}
