package com.graze.pastoral.notes.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionHandle {


    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity internal_server_error(Exception ex) {
        logger.error("[EEDC][自定义错误][程序错误]:" + ex.getMessage());
        return new ResponseEntity(new UnifiedResult(false, "500", ex.getMessage(), null), HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Exception.class})
    public ResponseEntity bad_request(Exception ex) {
        logger.error("[EEDC][自定义错误][程序错误]:" + ex.getMessage());
        return new ResponseEntity(new UnifiedResult(false, "400", ex.getMessage(), null), HttpStatus.OK);
    }
}
