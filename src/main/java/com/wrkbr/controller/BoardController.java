package com.wrkbr.controller;

import com.wrkbr.domain.BoardAttachVO;
import com.wrkbr.domain.BoardVO;
import com.wrkbr.domain.Criteria;
import com.wrkbr.domain.PageDTO;
import com.wrkbr.mapper.UserMapper;
import com.wrkbr.service.BoardService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {

    @Setter(onMethod_ = @Autowired)
    private UserMapper userMapper;

    @Setter(onMethod_ = @Autowired)
    private BoardService boardService;

//    @GetMapping("/list")
//    public void list(Model model){
//        log.info("list()...");
//        model.addAttribute("list", boardService.getList());
//    }

    @GetMapping("/list")
    public void list(Criteria criteria, Model model, String setEditor, Principal principal){
        log.info("list()...");
        log.info("criteria: " + criteria);
        log.info("setEditor: " + setEditor);
        log.info("principal: " + principal);


        String username = null;
        String usernickname = null;

        if(principal != null){
            username = principal.getName();
            log.info("principal.getName(): " + username);

            usernickname = userMapper.readNickname(username);
            log.info("usernickname: " + usernickname);

            model.addAttribute("usernickname", usernickname);

        } else {
            model.addAttribute("usernickname", null);
        }


        model.addAttribute("list", boardService.getListWithPagination(criteria));
        model.addAttribute("pageDTO", new PageDTO(criteria, boardService.boardCount(criteria)));

        if(setEditor != null){
            model.addAttribute("setEditor", "default");
        }
    }

    @GetMapping("/insert")
    @PreAuthorize("isAuthenticated()")
    public void insert(){
        log.info("Get insert()...");
    }


    @GetMapping("/insertEditor")
    @PreAuthorize("isAuthenticated()")
    public String insertEditor(Principal principal, Model model){
        log.info("Get insertEditor()...");


        if(principal != null) {
            String usernickname = userMapper.readNickname(principal.getName());
            model.addAttribute("usernickname", usernickname);
            log.info("usernickname: " + usernickname);
        } else {
            model.addAttribute("usernickname", null);
        }

        return "/board/insertEditor";
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

    @GetMapping({"/readEditor", "/updateEditor"})
    public void readEditor(@RequestParam("bno") Long bno, @ModelAttribute("criteria") Criteria criteria, Model model, Principal principal){
        log.info("readEditor() or updateEditor()...");

        if(principal != null) {
            String usernickname = userMapper.readNickname(principal.getName());
            model.addAttribute("usernickname", usernickname);
            log.info("usernickname: " + usernickname);

        } else {
            model.addAttribute("usernickname", null);
        }
        BoardVO boardVO = boardService.read(bno);
        log.info("boardVO: " + boardVO);
        model.addAttribute("boardVO", boardVO);
    }


    @PostMapping("/update")
    @PreAuthorize("principal.username == #boardVO.writerId")
    public String update(BoardVO boardVO, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes redirectAttributes){
        log.info("Post update()...");

        if(boardService.update(boardVO))
            redirectAttributes.addFlashAttribute("result", "successfully updated");

        return "redirect:/board/list" + criteria.getListLink();
    }

    @PostMapping("/delete")
    @PreAuthorize("principal.username == #writerId")
    public String delete(Long bno, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes redirectAttributes, String writerId){
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


    // Called "this method" when "delete method" called
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



    @GetMapping("/replyEditor")
    public void replyEditor(BoardVO boardVO, Model model, Criteria criteria, Principal principal){

        log.info("replyEditor GET...");
        log.info("boardVO: " + boardVO);

        if(principal != null){
            String usernickname = userMapper.readNickname(principal.getName());
            model.addAttribute("usernickname", usernickname);
            log.info("usernickname: " + usernickname);

        } else {
            model.addAttribute("usernickname", null);
        }


        model.addAttribute("boardVO", boardVO);
        model.addAttribute("criteria", criteria);

    }


    @PostMapping("/replyEditor")
    public String replyEditorPost(BoardVO boardVO, Model model, Criteria criteria, RedirectAttributes redirectAttributes){

        log.info("replyEditor POST...");

        boardService.replyInsertSelectKey(boardVO);
        redirectAttributes.addFlashAttribute("result", "successfully insert");

        //model.addAttribute("criteria", criteria);
        return "redirect:/board/list" + criteria.getListLink();

    }





}
