package com.wrkbr.mapper;

import com.wrkbr.domain.UserVO;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Log4j
public class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void readTest(){
        log.info("mapper: " + mapper);
        UserVO vo = mapper.read("test01");
        log.info("vo: " + vo);
    }

}
