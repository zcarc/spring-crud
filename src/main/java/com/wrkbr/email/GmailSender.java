package com.wrkbr.email;

import lombok.extern.log4j.Log4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@Log4j
public class GmailSender {

    private static String id = "id";
    private static String pw = "pw";

    private static JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();


    public static String tryMail(String userEmail, String protocol, String host, HttpServletRequest request) throws MessagingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        log.info("tryMail()...");

        log.info("protocol + host + request.getContextPath(): " + protocol + "+" + host + "+" + request.getContextPath());
        log.info("tryMail - userEmail: " + userEmail);

        String key = new SHA256().getSHA256(userEmail);
        log.info("tryMail - String key = new SHA256().getSHA256(userEmail): " + key);

        MimeMessage mimeMessage = getMailSender().createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setSubject("회원가입 인증 메일입니다.");
        mimeMessageHelper.setText(new StringBuffer()

                .append("<h1>다음 링크에 접속하여 이메일 인증을 할 수 있습니다.</h1>")
                .append("<a href='"+ protocol + host + request.getContextPath()+ "/" + "emailVerify?code=")
                .append(key)
                .append("&userEmail=")
                .append(userEmail)
                .append("' target='_blank'>이메일 인증하기</a>")
                .toString(), true);

        mimeMessageHelper.setFrom(id, host + "에서 보낸 메일입니다.");
        mimeMessageHelper.setTo(userEmail);
        javaMailSenderImpl.send(mimeMessage);

        return key;
    }


    public static JavaMailSenderImpl getMailSender() {

        javaMailSenderImpl.setHost("smtp.gmail.com");
        javaMailSenderImpl.setPort(465);
        javaMailSenderImpl.setProtocol("smtp");
        javaMailSenderImpl.setUsername(id);
        javaMailSenderImpl.setPassword(pw);

        javaMailSenderImpl.setJavaMailProperties(getProperties());

        return javaMailSenderImpl;
    }


    public static Properties getProperties() {

        Properties prop = new Properties();

        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.sarttls.enable", "false");
        prop.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.quitwait", "false");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.debug", "true");

        return prop;

    }


}