package com.wrkbr.security;

import com.wrkbr.domain.UserVO;
import com.wrkbr.service.UserService;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml","file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
        "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class UserTest {

    @Autowired
    private PasswordEncoder pwEncoder;

    @Autowired
    private DataSource ds;

    @Autowired
    private UserService userService;

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
    public void tesUpdate(){

        String sql = "update user_table set userpw = ? where userid = ?";

        try(Connection con = ds.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setString(1, pwEncoder.encode("pw" + 90));
            pstmt.setString(2, "test01");

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


    @Test
    public void testRegister(){

        UserVO userVO = new UserVO();
        userVO.setUserid("test02");
        userVO.setUserpw(pwEncoder.encode("test91"));
        userVO.setUsername("테스트02");
        userVO.setUserPhone("010-2222-2222");
        userVO.setUserGender("m");
        userVO.setUserEmail("test02@test02.com");

        userService.insert(userVO, "ROLE_MEMBER");

    }

    @Test
    public void justTest(){
        log.info("test");
    }
}
