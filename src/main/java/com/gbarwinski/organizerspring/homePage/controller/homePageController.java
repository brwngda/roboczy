package com.gbarwinski.organizerspring.homePage.controller;

import com.gbarwinski.organizerspring.main_content.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class homePageController {

    @GetMapping({"/home", "/"})
    public String showHomePage(Model model) {
        model.addAttribute("presentYear", LocalDateTime.now().getYear());
        return "homePage/index";
    }

    @ModelAttribute
    public void AddAttributes(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User appUser = null;
        if (session != null)
            appUser = (User) session.getAttribute("appUser");

        if (appUser != null) {
            model.addAttribute("ActualUser", appUser);
        } else
            model.addAttribute("ActualUser", new User());
    }

}

