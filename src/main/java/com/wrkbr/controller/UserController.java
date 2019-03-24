package com.wrkbr.controller;

import com.wrkbr.domain.ProfileVO;
import com.wrkbr.domain.UserVO;
import com.wrkbr.email.EmailChecker;
import com.wrkbr.email.GenerateChar;
import com.wrkbr.email.GmailSender;
import com.wrkbr.mapper.UserMapper;
import com.wrkbr.service.UserService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j
public class UserController {


    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder pwEncoder;

    @Setter(onMethod_ = @Autowired)
    private UserMapper userMapper;

    @Setter(onMethod_ = @Autowired)
    private UserService userService;



    // key: hashKey, checkedKey
    private static Map<String, EmailChecker> emailCheckerMap = new HashMap<>();

    // 회원가입 GET
    @GetMapping("/register")
    public String register(Model model) {
        log.info("register GET...");

        model.addAttribute("userVO", new UserVO());

        return "register/register";

    }

    // 회원가입 POST
   @PostMapping("/register")
    public String register(@Valid UserVO vo, BindingResult result, Model model, HttpServletResponse response) throws Exception {
       log.info("register POST...");

        log.info("UserVO:" + vo);


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

    // 이메일 인증 확인
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


    // 프로필 수정 페이지
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Principal principal, Model model){
        log.info("profile...");

        String username = null;
        String usernickname = null;
        ProfileVO profileVO = null;


        if(principal != null) {

            username = principal.getName();
            usernickname = userMapper.readNickname(username);
            log.info("usernickname: " + usernickname);

             profileVO = userMapper.readProfile(username);
            log.info("profileVO: " + profileVO);

            if(profileVO.getKakao_id() != null || profileVO.getFacebook_id() != null || profileVO.getGoogle_id() != null){
                model.addAttribute("social", "true");
            }

            if(usernickname != null)
                model.addAttribute("usernickname", usernickname);
        }


        return "user/profile";
    }

    // 프로필 수정 POST
    @PostMapping("/profileSave")
    @PreAuthorize("isAuthenticated()")
    public String profileSave(UserVO userVO, Principal principal){
        log.info("profileSave... userVO: " + userVO);
        log.info("UserVO: " + userVO);

        String userpw = userVO.getUserpw();
        if(userpw == null)
            userpw = "";

        if(principal != null){
            log.info("principal: " + principal);

            // 일반 사용자가 비밀번호를 입력했거나 || 소셜 로그인 사용자가 아닌경우
            if(!userpw.equals("")){

                userVO.setUserid( principal.getName() );
                userVO.setUserpw( pwEncoder.encode(userpw) );
                userMapper.updateUser(userVO);
                log.warn("after set userVO: " + userVO);

              // 일반 사용자가 비밀번호를 입력하지 않았거나 || 소셜 로그인 사용자인 경우
            } else if(userpw.equals("")){

                userVO.setUserid( principal.getName() );
                userMapper.updateUser(userVO);
                log.warn("after set userVO: " + userVO);

            }

        }


        return "redirect:board/list";
    }

    // 닉네임 중복 확인
    @PostMapping(value = "/checkNickname", produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public ResponseEntity<String> checkNickname(@RequestBody String checkNickname){
        log.info("checkNickname... checkNickname: " + checkNickname);

        String resultNickname = userMapper.readNickname(checkNickname);
        log.info("resultNickname: " + resultNickname);

        return resultNickname == null
                ? new ResponseEntity<>("X", HttpStatus.OK)
                : new ResponseEntity<>("O", HttpStatus.OK);

    }

    // 아이디 중복 확인
    @PostMapping(value = "/checkId", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> checkId(@RequestBody String userid){
        log.info("userid... userid: " + userid);

        String resulrId = userMapper.readId(userid);
        log.info("resulrId: " + resulrId);

        return resulrId == null
                ? new ResponseEntity<>("X", HttpStatus.OK)
                : new ResponseEntity<>("O", HttpStatus.OK);
    }

    // 비밀번호 찾기 페이지
    @GetMapping("/findPW")
    public String findPW(){
        log.info("findPW...");
        return "user/findPW";
    }


    // 이메일 중복 확인
    @PostMapping(value = "/checkEmail", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> checkEmail(@RequestBody String useremail){
        log.info("checkEmail... useremail: " + useremail);

        String resultEmail = userMapper.readEmail(useremail);
        log.info("resultEmail: " + resultEmail);

        return resultEmail == null
                ? new ResponseEntity<>("X", HttpStatus.OK)
                : new ResponseEntity<>("O", HttpStatus.OK);
    }


    // 임시 비밀번호 발송
    @PostMapping(value = "/requestPW", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> requestPW(@RequestBody String userEmail, HttpServletRequest request) {
        log.info("requestPW...");

        String reuqestHost = request.getHeader("host");
        String requestProtocol = request.getRequestURL().toString();
        String host = null;
        log.info("userEmail: " + userEmail);
        log.info("request.getHeader(\"host\"): " + reuqestHost);
        log.info("request.getRequestURL().toString(): " + requestProtocol);

        // Get a host name
        if( reuqestHost.contains("localhost")) {
            host = reuqestHost;

        } else if ( reuqestHost.contains("boardAPI") ) {
            host = reuqestHost;
        }

        // Get a protocol name
        String protocol = null;
        if ( requestProtocol.indexOf("https") > -1 ) {
            protocol = "https://";

        } else if ( requestProtocol.indexOf("http") > -1 ) {
            protocol = "http://";
        }


        // 이메일 발송
        GenerateChar generateChar = new GenerateChar();
        String tempPW = generateChar.generateChar();
        sendPW(userEmail, host, tempPW);

        // 비밀번호 변경
        userMapper.updatePW(pwEncoder.encode(tempPW), userEmail);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    private static void sendPW(String userEmail, String host, String tempPW){


        MimeMessage mimeMessage = GmailSender.getMailSender().createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setSubject("임시 비밀번호 입니다.");

            mimeMessageHelper.setText(new StringBuffer()

                    .append("<h4 style='display:inline;'>임시 비밀번호: </h4>")
                    .append("<span>" + tempPW + "</span>")
                    .toString(), true);

            mimeMessageHelper.setFrom(GmailSender.getId(), host + "에서 보낸 메일입니다.");
            mimeMessageHelper.setTo(userEmail);
            GmailSender.javaMailSenderImpl.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }







}
