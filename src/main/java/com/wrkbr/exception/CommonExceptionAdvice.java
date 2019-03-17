package com.wrkbr.exception;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {

//    @ExceptionHandler(Exception.class)
//    public String exception(Exception e, Model model) {
//        log.error("exception()...");
//        e.printStackTrace();
//
//        model.addAttribute("exception", "에러가 발생했습니다.");
//
//        return "exception/errorPage";
//    }


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(NoHandlerFoundException ex) {
        log.info("notFound()...");
        return "exception/error404";
    }

}
