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

}