package com.opipo.terraincognitaserver.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opipo.terraincognitaserver.dto.Role;
import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final String ROLES_FIELD_NAME = "roles";
    public static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.find(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(buildSimpleGrantedAuthority("USER"));
        user.getRoles().stream().map(Role::getAuthority).map(this::buildSimpleGrantedAuthority)
                .forEach(authorities::add);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    private SimpleGrantedAuthority buildSimpleGrantedAuthority(String rol) {
        return new SimpleGrantedAuthority(ROLE_PREFIX + rol);
    }

}