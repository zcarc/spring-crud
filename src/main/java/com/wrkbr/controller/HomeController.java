package com.wrkbr.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Log4j
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale) {
        logger.info("Welcome home! The client locale is {}.", locale);

        return "redirect:/board/list";
    }



}