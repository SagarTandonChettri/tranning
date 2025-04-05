package com.example.testCRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class TeacherApiReponse<T> {
    private String status;
    private int statusCode;
    private String message;
    private T data;
}
