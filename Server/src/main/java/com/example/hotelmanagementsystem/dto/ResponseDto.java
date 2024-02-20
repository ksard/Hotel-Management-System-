package com.example.hotelmanagementsystem.dto;

public class ResponseDto<T> {
    public String status;
    public int statusCode;
    public String statusMessage;
    public T content;
}
