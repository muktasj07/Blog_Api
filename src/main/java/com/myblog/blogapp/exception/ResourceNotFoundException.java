package com.myblog.blogapp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resourcename;
    private String fieldName;
    private long fieldValue;



    public ResourceNotFoundException(String resourcename, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s: %s", resourcename, fieldName, fieldValue));// Post not found with id:1
        this.resourcename = resourcename;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourcename() {
        return resourcename;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }

}
