package com.wrkbr.controller;

import com.wrkbr.domain.Criteria;
import com.wrkbr.domain.ReplyPagesDTO;
import com.wrkbr.domain.ReplyVO;
import com.wrkbr.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/replies")
@AllArgsConstructor
@Log4j
public class ReplyController {

    private ReplyService replyService;

    // 댓글 작성
    @PostMapping(value = "/insert", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> insert (@RequestBody ReplyVO replyVO) {
        log.info("replyVO: " + replyVO);

        int result = replyService.insert(replyVO);
        return result == 1
                ? new ResponseEntity<>("Successfully inserted.", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 댓글 페이징
    @GetMapping(value="/pages/{bno}/{currentPage}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ReplyPagesDTO> getListWithPagination(@PathVariable("bno") Long bno,
                                                               @PathVariable("currentPage") int currentPage){
        log.info("getListWithPagination()... " + "bno: " + bno + " currentPage: " + currentPage);

        Criteria criteria = new Criteria(currentPage, 10);
        return new ResponseEntity<>(replyService.getListWithPagination(bno, criteria), HttpStatus.OK);
    }

    // 댓글 읽기
    @GetMapping(value = "/{rno}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<ReplyVO> read(@PathVariable("rno") Long rno) {
        log.info("read()..." + " rno: " + rno);

        return new ResponseEntity<>(replyService.read(rno), HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping(value = "/{rno}", produces = {MediaType.TEXT_PLAIN_VALUE})
    @PreAuthorize("principal.username == #replyVO.replyerId")
    public ResponseEntity<String> delete(@PathVariable("rno") Long rno, @RequestBody ReplyVO replyVO) {
        log.info("delete()..." + " rno: " + rno + " replyVO.replyer: " + replyVO.getReplyer());

        int result = replyService.delete(rno);
        return result == 1
                ? new ResponseEntity<>("Successfully deleted.", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 댓글 수정
    @RequestMapping(value = "/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH},
                    consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    @PreAuthorize("principal.username == #replyVO.replyerId")
    public ResponseEntity<String> update (@RequestBody ReplyVO replyVO, @PathVariable("rno") Long rno){
        log.info("update()..." + " replyVO: " + replyVO );
        log.info("rno: " + rno);

        replyVO.setRno(rno);
        log.info("after setRno replyVO: " + replyVO);

        int result = replyService.update(replyVO);
        return result == 1
                ? new ResponseEntity<>("Successfully modified.", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
