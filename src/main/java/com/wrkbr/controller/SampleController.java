package com.wrkbr.controller;

import lombok.extern.log4j.Log4j;
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

}
