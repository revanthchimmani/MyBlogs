package com.revanth.blogs.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class BlogAPIException extends RuntimeException {
    private  HttpStatus status;
    private String message;

    public BlogAPIException(String message, HttpStatus status, String message1){
        super(message);
        this.message=message1;
        this.status=status;
    }

}
