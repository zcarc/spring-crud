package com.wrkbr.controller;

import com.wrkbr.domain.BoardAttachVO;
import com.wrkbr.domain.BoardVO;
import com.wrkbr.domain.Criteria;
import com.wrkbr.domain.PageDTO;
import com.wrkbr.service.BoardService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


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
    @PreAuthorize("isAuthenticated()")
    public void insert(){
        log.info("Get insert()...");
    }


    @PostMapping("/insert")
    @PreAuthorize("isAuthenticated()")
    public String insert(BoardVO boardVO, RedirectAttributes redirectAttributes){
        log.info("insert()...");
        log.info("boardVO: " + boardVO);

        if(boardVO.getAttachVOList() != null)
            boardVO.getAttachVOList().forEach(log::info);

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
    @PreAuthorize("principal.username == #boardVO.writer")
    public String update(BoardVO boardVO, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes redirectAttributes){
        log.info("Post update()...");

        if(boardService.update(boardVO))
            redirectAttributes.addFlashAttribute("result", "successfully updated");

        return "redirect:/board/list" + criteria.getListLink();
    }

    @PostMapping("/delete")
    @PreAuthorize("principal.username == #writer")
    public String delete(Long bno, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes redirectAttributes, String writer){
        log.info("delete()...");
        log.info("bno: " + bno);

        List<BoardAttachVO> boardAttachVOList = boardService.getListAttach(bno);
        log.info("boardAttachVOList: " + boardAttachVOList);

        // boardService.delete :
        // boardAttachMapper.deleteAll(bno),
        // boardMapper.delete(bno)
        if(boardService.delete(bno) && (boardAttachVOList != null || boardAttachVOList.size() > 0)) {

            // Physical deletion.
            deleteFiles(boardAttachVOList);

            redirectAttributes.addFlashAttribute("result", "successfully deleted");
        }

        return "redirect:/board/list" + criteria.getListLink();
    }

    @GetMapping(value = "/getListAttach", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<BoardAttachVO>> getListAttach(Long bno){
        log.info("getListAttach... bno" + bno);
        return new ResponseEntity<>(boardService.getListAttach(bno), HttpStatus.OK);
    }


    // Called this method when called delete mapping.
    // Physical deletion.
    private void deleteFiles(List<BoardAttachVO> attachList) {
        log.info("deleteFiles...");
        log.info("attachList: " + attachList);

        if(attachList == null || attachList.size() <= 0) {
            log.info("attachList doesn't exist.");
            return;
        }

        // 물리적인 파일 삭제
        attachList.forEach(attach -> {
            try {

                Path file = Paths.get("C:\\upload\\" + attach.getUploadFolder() + "\\" + attach.getUuid() + "_" + attach.getFileName());
                log.info("Path file = Paths.get(\"C:\\\\upload\\\\\" + attach.getUploadFolder() + \"\\\\\" + attach.getUuid() + \"_\" + attach.getFileName()): " + file);

                boolean deletedFile = Files.deleteIfExists(file);
                log.info("Files.deleteIfExists(file): " + deletedFile);

                // 파일의 "contentType"이 "image"일 경우 섬네일까지 삭제
                if(Files.probeContentType(file).startsWith("image")) {

                    Path thumbnail = Paths.get("C:\\upload\\" + attach.getUploadFolder() + "\\s_" + attach.getUuid() + "_" + attach.getFileName());
                    log.info("Path thumbnail = Paths.get(\"C:\\\\upload\\\\\" + attach.getUploadPath() + \"\\\\s_\" + attach.getUuid() + \"_\" + attach.getFileName()): " + thumbnail);

                    boolean deletedThumbnail = Files.deleteIfExists(thumbnail);
                    log.info("Files.deleteIfExists(thumbnail): " + deletedThumbnail);

                }

            }catch (Exception e) {
                log.info("delete file error: " + e.getMessage());
                e.printStackTrace();
            }

        });
    }



}
