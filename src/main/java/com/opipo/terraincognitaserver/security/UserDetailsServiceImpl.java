package com.opipo.terraincognitaserver.security;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    private List<GrantedAuthority> buildUserAuthority(String role) {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority(role));
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.find(username);
        List<GrantedAuthority> authorities = buildUserAuthority("Role");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                emptyList());
    }

}