package com.graze.pastoral.notes.domain.file;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/20 12:21
 */

public class FileException extends RuntimeException{
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
