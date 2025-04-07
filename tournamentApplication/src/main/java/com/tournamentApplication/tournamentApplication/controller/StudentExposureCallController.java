package com.tournamentApplication.tournamentApplication.controller;

import com.tournamentApplication.tournamentApplication.dto.StudentApiDto;
import com.tournamentApplication.tournamentApplication.dto.StudentApiDtoResponse;
import com.tournamentApplication.tournamentApplication.service.StudentApiServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentExposureCallController {

    @Autowired
    private StudentApiServiceImpl studentApiService;

    @GetMapping("/all")
    public ResponseEntity<StudentApiDtoResponse<List<StudentApiDto>>> getAllStudent (){
        log.info("Received a Request to get All Student");
        List<StudentApiDto> student = studentApiService.getAllStudent();
        log.info("Revived list of Student from service list: {}",student);

        if(student.isEmpty()){
            return ResponseEntity.status(404).body(new StudentApiDtoResponse<>(
                    "Error",
                    404,
                    "Student Not Found",
                    null));
        }

        return ResponseEntity.status(200).body(new StudentApiDtoResponse<>(
                "Success",
                200,
                "Student fetched successfully",
                student));
    }
}
