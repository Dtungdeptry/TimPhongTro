package com.example.TimPhongTro.Exception;

import org.springframework.http.HttpStatus;

public class ErrorRespone {
    private HttpStatus status;
    private String message;

    public ErrorRespone() {

    }

    public ErrorRespone(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
