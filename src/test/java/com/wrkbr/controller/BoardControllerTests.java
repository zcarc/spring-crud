package com.wrkbr.controller;

import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@WebAppConfiguration // WebApplicationContext needs this Annotation.
@Log4j
public class BoardControllerTests {

    @Autowired
    private WebApplicationContext ctx;

    // MockMvc is for Testing.
    private MockMvc mockMvc;


    // This method is executed
    // before all test methods are executed.
    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void testList() throws Exception {

        log.info("testList()..." +

                mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
                .andReturn()
                .getModelAndView()
                .getModelMap()

                );

    } // testList()


    @Test
    public void testInsert() throws Exception {

        String resultPage = mockMvc.perform( MockMvcRequestBuilders.post("/board/insert")
                .param("title", "테스트제목6")
                .param("content", "테스트내용6")
                .param("writer", "작성자6")
        ) .andReturn().getModelAndView().getViewName();

        log.info("resultPage: " + resultPage);
    }

    @Test
    public void testRead() throws Exception {
        log.info("testRead()..." +

                mockMvc.perform(MockMvcRequestBuilders.get("/board/read").param("bno", "1"))
                        .andReturn().getModelAndView().getModelMap()
        );

    } // testRead()

    @Test
    public void testUpdate() throws Exception {
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/update")
                .param("bno", "5")
                .param("title","테스트제목5 수정")
                .param("content", "테스트내용5 수정")
                .param("writer", "작성자5 수정")
        ).andReturn().getModelAndView().getViewName();

        log.info("resultPage: " + resultPage);
    }

    @Test
    public void testDelete() throws Exception {
        String resultMap = mockMvc.perform(MockMvcRequestBuilders.post("/board/delete").param("bno", "95"))
                .andReturn().getModelAndView().getViewName();

        log.info("resultMap: " + resultMap);
    }

    @Test
    public void testGetListWithPagination() throws Exception {
        log.info(
                    mockMvc.perform(MockMvcRequestBuilders.get("/board/list").param("currentPage","2").param("displayRecords", "30")
                                    ).andReturn().getModelAndView().getModelMap()
                );
    }

}
