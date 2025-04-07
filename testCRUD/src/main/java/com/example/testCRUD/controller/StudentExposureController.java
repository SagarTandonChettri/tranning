package com.example.testCRUD.controller;

import com.example.testCRUD.entity.Student;
import com.example.testCRUD.service.StudentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentExposureController {

    @Autowired
    private StudentServiceImpl studentService;

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudent(){
        log.info("Received GET REQUEST getAllStudent");
        List<Student> students = studentService.fetchStudentList();
        log.info("Received fetch list: {}",students);

        if(students.isEmpty()){
            log.info("List is Empty");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        log.info("total Entry {}, tournamentWinner: {}",students.size(),students);
        return ResponseEntity.ok(students);
    }
}
