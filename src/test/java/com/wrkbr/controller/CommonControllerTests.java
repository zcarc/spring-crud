package com.wrkbr.controller;


import com.wrkbr.mapper.UserMapper;
import com.wrkbr.service.UserService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml","file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
        "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@WebAppConfiguration // WebApplicationContext needs this Annotation.
@Log4j
public class CommonControllerTests {

    @Setter(onMethod_ = @Autowired)
    private UserService userService;

    @Setter(onMethod_ = @Autowired)
    private UserMapper userMapper;



}
