package com.gbarwinski.organizerspring.registration_login_logout.services;

import com.gbarwinski.organizerspring.main_content.model.User;
import com.gbarwinski.organizerspring.main_content.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MyUserDetailsService implements UserDetailsService {


    private final UserService userService;


    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user found with username: " + email);
        }
        return toUserDetails(user);
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }

    }





