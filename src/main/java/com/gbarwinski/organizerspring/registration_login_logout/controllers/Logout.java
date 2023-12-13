package com.gbarwinski.organizerspring.registration_login_logout.controllers;

import com.gbarwinski.organizerspring.main_content.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Slf4j
@Controller
public class Logout {
    @RequestMapping(value = "/logout")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("appUser");
        return "login/loginPage";
    }
}
