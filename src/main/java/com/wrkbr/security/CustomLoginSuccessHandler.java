package com.wrkbr.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        logger.warn("Login Success.");
        logger.info("authentication.getAuthorities(): " + authentication.getAuthorities().toString());


        List<String> roleNames = new ArrayList<>();


        authentication.getAuthorities().forEach(authority -> {

            logger.info("authority: " + authority);
            logger.info("authority.getAuthority(): " + authority.getAuthority());


            roleNames.add(authority.getAuthority());

        });


        logger.warn("Role names: " + roleNames);


        if(roleNames.contains("ROLE_ADMIN")) {

            response.sendRedirect("/board/list");
            return;
        }

        if(roleNames.contains("ROLE_MEMBER")) {

            response.sendRedirect("/board/list");
            return;

        }

        response.sendRedirect("/");

    }

}