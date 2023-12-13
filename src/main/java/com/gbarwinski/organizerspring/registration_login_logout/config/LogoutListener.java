package com.gbarwinski.organizerspring.registration_login_logout.config;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
        List<SecurityContext> lstSecurityContext = event.getSecurityContexts();
        UserDetails ud;
        for (SecurityContext securityContext : lstSecurityContext)
        {
            ud = (UserDetails) securityContext.getAuthentication().getPrincipal();
            // ...
        }
    }

}