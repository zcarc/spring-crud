package com.wrkbr.controller;

import com.wrkbr.domain.BoardVO;
import com.wrkbr.domain.Criteria;
import com.wrkbr.domain.PageDTO;
import com.wrkbr.service.BoardService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {

    @Setter(onMethod_ = @Autowired)
    private BoardService boardService;

//    @GetMapping("/list")
//    public void list(Model model){
//        log.info("list()...");
//        model.addAttribute("list", boardService.getList());
//    }

    @GetMapping("/list")
    public void list(Criteria criteria, Model model){
        log.info("list()...");

        log.info("criteria: " + criteria);

        model.addAttribute("list", boardService.getListWithPagination(criteria));
        model.addAttribute("pageDTO", new PageDTO(criteria, boardService.boardCount(criteria)));
    }

    @GetMapping("/insert")
    public void insert(){
        log.info("Get insert()...");
    }


    @PostMapping("/insert")
    public String insert(BoardVO boardVO, RedirectAttributes redirectAttributes){
        log.info("insert()...");

        log.info("before insertSelectKey..." + boardVO.getBno());
        boardService.insertSelectKey(boardVO);
        log.info("after insertSelectKey..." + boardVO.getBno());

        redirectAttributes.addFlashAttribute("result", boardVO.getBno());
        return "redirect:/board/list";
    }


    @GetMapping({"/read", "/update"})
    public void read(@RequestParam("bno") Long bno, @ModelAttribute("criteria") Criteria criteria, Model model){
        log.info("read() or update()...");
        model.addAttribute("boardVO", boardService.read(bno));
    }


    @PostMapping("/update")
    public String update(BoardVO boardVO, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes redirectAttributes){
        log.info("Post update()...");

        if(boardService.update(boardVO))
            redirectAttributes.addFlashAttribute("result", "successfully updated");

        return "redirect:/board/list" + criteria.getListLink();
    }

    @PostMapping("/delete")
    public String delete(Long bno, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes redirectAttributes){
        log.info("delete()...");
        log.info("bno: " + bno);

        if(boardService.delete(bno))
            redirectAttributes.addFlashAttribute("result","successfully deleted");

        return "redirect:/board/list" + criteria.getListLink();
    }



}
