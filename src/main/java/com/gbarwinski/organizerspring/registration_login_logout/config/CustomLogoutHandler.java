package com.gbarwinski.organizerspring.registration_login_logout.config;

import com.gbarwinski.organizerspring.main_content.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse httpServletResponse,
                       Authentication authentication) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("appUser");
        log.info("User " + user.getEmail() + " was logged out");
    }
}

