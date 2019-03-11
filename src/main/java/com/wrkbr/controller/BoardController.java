package com.wrkbr.controller;

import com.wrkbr.domain.BoardVO;
import com.wrkbr.service.BoardService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public void list(Model model){
        log.info("list()...");
        model.addAttribute("list", boardService.getList());
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
    public void read(@RequestParam("bno") Long bno, Model model){
        log.info("read() or update()...");
        model.addAttribute("boardVO", boardService.read(bno));
    }


    @PostMapping("/update")
    public String update(BoardVO boardVO, RedirectAttributes redirectAttributes){
        log.info("Post update()...");

        if(boardService.update(boardVO))
            redirectAttributes.addFlashAttribute("result", "successfully updated");

        return "redirect:/board/list";
    }

    @PostMapping("/delete")
    public String delete(Long bno, RedirectAttributes redirectAttributes){
        log.info("delete()...");
        log.info("bno: " + bno);

        if(boardService.delete(bno))
            redirectAttributes.addFlashAttribute("result","successfully deleted");

        return "redirect:/board/list";
    }



}
