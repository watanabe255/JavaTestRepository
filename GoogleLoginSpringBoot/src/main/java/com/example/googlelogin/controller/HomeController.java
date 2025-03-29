package com.example.googlelogin.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("/home")
    public ModelAndView home(HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("login");

        if (isLoggedIn == null || !isLoggedIn) {
            return new ModelAndView("redirect:/login.html");
        }

        return new ModelAndView("home");
    }
}
