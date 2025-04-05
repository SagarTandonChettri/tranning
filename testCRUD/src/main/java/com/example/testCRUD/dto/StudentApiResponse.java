package com.example.testCRUD.dto;

import com.example.testCRUD.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@NoArgsConstructor
@Slf4j
@AllArgsConstructor
public class StudentApiResponse<T> {
    private String status;
    private int statusCode;
    private String message;
    private T data;

//    List<Student> maleStuendts;
//    List<Student> femaleStuendts;

}
