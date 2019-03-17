package com.wrkbr.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j
public class SampleController {

    @GetMapping("/home")
    public String Home() {

        log.info("Get Home...");

        return "home";
    }

    @GetMapping("/sample/all")
    public String all(){
        log.info("/sample/all...");
        return "/sample/all";
    }
    @GetMapping("/sample/member")
    public String member(){
        log.info("/sample/member...");
        return "/sample/member";
    }

    @GetMapping("/sample/admin")
    public String admin(){
        log.info("/sample/admin...");
        return "/sample/admin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    @GetMapping("/annoMember")
    public void doMember2() {
        log.info("logined");
    }
}

