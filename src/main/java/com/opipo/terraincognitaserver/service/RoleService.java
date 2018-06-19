package com.opipo.terraincognitaserver.service;

import org.springframework.security.core.Authentication;

import com.opipo.terraincognitaserver.dto.Role;

public interface RoleService extends ServiceDTOInterface<Role, String> {
    public boolean canManageRole(Authentication authentication, String role);
}
