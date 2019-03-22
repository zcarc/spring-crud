package com.wrkbr.service;

import com.wrkbr.domain.PlatformVO;
import com.wrkbr.mapper.UserMapper;
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
public class UserServiceTests {

    @Setter(onMethod_ = @Autowired)
    private UserMapper userMapper;

    @Setter(onMethod_ = @Autowired)
    private UserService userService;

    @Test
    public void testInsertAllInfoPlatformUser(){

        String platformName = "kakao";

        PlatformVO platformVO = new PlatformVO();

        platformVO.setKakao_id("YYYYYYYYYYYYYYY");
        platformVO.setKa_generate("GFHDdolhDF");

        String prefix = userMapper.selectPrefix(platformVO);
        log.info("Select prefix: " + prefix);

        if (prefix == null) {
            log.info("prefix is NULL...");

            userService.insertAllInfoPlatformUser(platformVO);
            prefix = userMapper.selectPrefix(platformVO);

            log.info("After insert prefix: " + prefix);
        }


    }

}
