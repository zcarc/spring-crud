package com.wrkbr.security;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class UserTest {

    @Autowired
    private PasswordEncoder pwEncoder;

    @Autowired
    private DataSource ds;

    @Test
    public void testInsert(){

        String sql = "insert into user_table(userid, userpw, username, userGender, userPhone, userEmail) values(?, ?, ?, ?, ?, ?)";

        try(Connection con = ds.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setString(1, "test01");
            pstmt.setString(2, pwEncoder.encode("test01"));
            pstmt.setString(3, "테스트01");
            pstmt.setString(4, "m");
            pstmt.setString(5, "010-0000-0000");
            pstmt.setString(6, "test01@test01.com");

            int result = pstmt.executeUpdate();
            log.info("result: " + result);


        }catch(Exception e){
            e.printStackTrace();
        }

    }


    @Test
    public void testInsertAuth(){

        String sql = "insert into user_table_auth(userid, auth) values(?, ?)";

        try(Connection con = ds.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setString(1, "test01");
            pstmt.setString(2, "ROLE_MEMBER");


            int result = pstmt.executeUpdate();
            log.info("result: " + result);


        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
