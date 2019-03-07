package com.wrkbr.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j
public class CommonController {

    @GetMapping("/accessError")
    public void accessDenied(Authentication auth, Model model){
        log.info("Access Denied - auth: " + auth);
        model.addAttribute("msg", "권한이 없는 사용자입니다.");
    }

    @GetMapping("/customLogin")
    public void customLogin(String logout, String error, Model model){
        log.info("logout: " + logout);
        log.info("error: " + error);

        if(logout != null)
            model.addAttribute("logout", "You have been signed out.");

        if(error != null)
            model.addAttribute("error", "Login failed. Check your account.");
    }

}

