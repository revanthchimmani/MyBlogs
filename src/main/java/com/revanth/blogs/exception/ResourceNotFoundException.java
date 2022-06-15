package com.revanth.blogs.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private Long fieldValue;
}
