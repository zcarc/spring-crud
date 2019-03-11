package com.wrkbr.controller;

import com.wrkbr.domain.UserVO;
import com.wrkbr.email.EmailChecker;
import com.wrkbr.email.GmailSender;
import com.wrkbr.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j
public class UserController {


    @Autowired(required = false)
    private PasswordEncoder pwEncoder;


    @Autowired
    private UserService userService;

    // key: hashKey, checkedKey
    private static Map<String, EmailChecker> emailCheckerMap = new HashMap<>();


    @GetMapping("/register")
    public String register(Model model) {
        log.info("register GET...");

        model.addAttribute("userVO", new UserVO());

        return "register/register";

    }


   @PostMapping("/register")
    public String register(@Valid UserVO vo, BindingResult result, Model model, HttpServletResponse response) throws Exception {
       log.info("register POST...");

        log.info("UserVO:" + vo);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter printWriter = null;


        if(result.hasErrors()) {
            log.error("에러가 발생했습니다.");
            return "register/register";

        } else {

            String regMail = vo.getUserEmail();
            EmailChecker emailChecker = null;

            try {
                emailChecker = emailCheckerMap.get(regMail);

                if(emailChecker.getUserMailChecked() != false) {

                    vo.setUserEmailHash(emailChecker.getUserMailHash());
                    vo.setUserEmailChecked(emailChecker.getUserMailChecked());

                    log.info("vo.getUserpw(): " + vo.getUserpw());
                    String encodedPw = pwEncoder.encode(vo.getUserpw());
                    vo.setUserpw(encodedPw);
                    log.info("after encodedPw vo.getUserpw(): " + vo.getUserpw());

                    userService.insert(vo, "ROLE_MEMBER");

                    //회원가입 전 emailCheckerMap
                    log.warn("before deleting userMail from emailCheckerMap" + emailCheckerMap.get(emailChecker.getUserMail()));

                    //회원가입 후 emailCheckerMap 삭제
                    emailCheckerMap.remove(emailChecker.getUserMail());
                    log.warn("after deleting userMail from emailCheckerMap" + emailCheckerMap.get(emailChecker.getUserMail()));

                    log.info(vo.getUserid()+": 회원가입 완료");

                    model.addAttribute("result", "O");

                } else {
                    model.addAttribute("result", "X");
                }

                return "register/resultSignUp";

            } catch(Exception e) {

                model.addAttribute("result", "X");
                return "register/resultSignUp";
            }
        } // else

    } // register POST


    // 회원가입 인증메일 발송
    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "/requestEmailAuth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String requestEmailAuth(@RequestBody Map<String, Object> map, HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException {
        log.info("requestEmailAuth...");

        String reuqestHost = request.getHeader("host");
        String requestProtocol = request.getRequestURL().toString();
        String host = null;
        log.info("reuqestHost: " + reuqestHost);
        log.info("requestProtocol: " + requestProtocol);

        // Get a host name
         if( reuqestHost.contains("localhost")) {
            host = reuqestHost;

        } else if ( reuqestHost.contains("boardAPI") ) {
            host = reuqestHost;
        }
//        if( reuqestHost.indexOf("localhost") > -1) {
//            host = reuqestHost;
//
//        } else if ( reuqestHost.indexOf("boardAPI") > -1 ) {
//            host = reuqestHost;
//        }

        // Get a protocol name
        String protocol = null;
        if ( requestProtocol.indexOf("https") > -1 ) {
            protocol = "https://";

        } else if ( requestProtocol.indexOf("http") > -1 ) {
            protocol = "http://";
        }

        String userEmail = (String)map.get("userEmail");
        log.info("protocol: " + protocol);

        String hashKey = GmailSender.tryMail(userEmail, protocol, host, request);
        EmailChecker emailChecker = new EmailChecker(userEmail, hashKey, false);

        emailCheckerMap.put(userEmail, emailChecker);


        EmailChecker getEmailChecker = emailCheckerMap.get(userEmail);
        log.info("requestEmailAuth - getEmailChecker.getUserMail(): " + getEmailChecker.getUserMail());
        log.info("requestEmailAuth - getEmailChecker.getUserMailHash(): " + getEmailChecker.getUserMailHash());

        return "success";

    }


    @PreAuthorize("isAnonymous()")
    @GetMapping("/emailVerify")
    public String emailVerify(@RequestParam Map<String, Object> emailRequestMap, Model model, HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
        log.info("emailVerify...");

        String emailReq = (String)emailRequestMap.get("userEmail");
        String codeReq = (String)emailRequestMap.get("code");


        EmailChecker userEmailChecker = emailCheckerMap.get(emailReq);
        log.info("emailVerify - uemailChecker: " + userEmailChecker);


        log.info("emailVerify - String emailReq = (String)emailRequestMap.get(\"userEmail\"): " + emailReq);
        log.info("emailVerify - String codeReq = (String)emailRequestMap.get(\"code\");: " + codeReq);
        System.out.println();
        log.info("emailVerify - userEmailChecker.getUserMail(): " + userEmailChecker.getUserMail());
        log.info("emailVerify - userEmailChecker.getUserMailHash(): " + userEmailChecker.getUserMailHash());


        boolean isEquals = userEmailChecker.equals(emailReq, codeReq);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter printWriter = response.getWriter()) {

            if(isEquals) {
                printWriter.println("<script>");
                printWriter.println("alert('이메일 인증 성공');");
                printWriter.println("self.close();");
                printWriter.println("</script>");

            } else {

                printWriter.println("<script>");
                printWriter.println("alert('이메일 인증 실패');");
                printWriter.println("self.close();");
                printWriter.println("</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "register/emailVerify";
    }


}
