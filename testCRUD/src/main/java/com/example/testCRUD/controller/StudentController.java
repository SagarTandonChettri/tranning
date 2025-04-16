package com.example.testCRUD.controller;

import com.example.testCRUD.dto.StudentApiResponse;
import com.example.testCRUD.dto.StudentByGenderResponse;
import com.example.testCRUD.entity.Student;
import com.example.testCRUD.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    //Post
    @PostMapping
    public ResponseEntity<StudentApiResponse<Student>> saveStudent(@RequestBody Student student){
        log.info("Received POST request to save Student:{}",student);
        Student savedStudent = studentService.saveStudent(student);
        log.info("Saved Complete savedStudent: {}",savedStudent);
        StudentApiResponse<Student> response = new StudentApiResponse<>(
                "success",
                201,
                "Student created successfully",
                savedStudent
        );
        log.info("Response - POST request to save Student: {}",response);
        return  ResponseEntity.status(201).body(response);
    }

    //Read
    @GetMapping
    public ResponseEntity<StudentApiResponse<List<Student>>> getAllStudents(){
        log.info("Received GET Request to getAllStudents");
        List<Student> student = studentService.fetchStudentList();
        //log.info("Received All student {}",student);
        log.info("Received All student");
        StudentApiResponse<List<Student>> response = new StudentApiResponse<>(
                "success",
                201,
                "Fetch complete",
                student
        );
        log.info("Response - GET Request to getAllStudents:{}",response);
        return ResponseEntity.ok(response);
    }


    //fetch data By Gender
    @GetMapping("/gender")
    public ResponseEntity<StudentApiResponse<StudentByGenderResponse>> getAllStudentByGender(){
        log.info("Received a Request to FETCH data Based on Gender ");

        List<Student> maleStudents = studentService.getStudentByGender("male");
        List<Student> femaleStudents = studentService.getStudentByGender("female");

        log.info("Received Male List: {}",maleStudents);
        log.info("Received Female List: {}", femaleStudents);

        StudentByGenderResponse genderResponse = new StudentByGenderResponse(maleStudents,femaleStudents);

        if(maleStudents.isEmpty() && femaleStudents.isEmpty()) {
            StudentApiResponse<StudentByGenderResponse> response = new StudentApiResponse<>(
                    "error",
                    404,
                    "No students found with gender: Male, Female",
                    null
            );
            log.info("Response - Request for FETCH getAllStudentByGender NO STUDENT FOUND: {}",response);
            return ResponseEntity.status(404).body(response);
        }
        StudentApiResponse<StudentByGenderResponse> response = new StudentApiResponse<>(
                "Success",
                200,
                "Students Found Based On Gender",
                genderResponse
        );
        log.info("Response - Request for FETCH getAllStudentByGender STUDENT FOUND: {}",response);
        return ResponseEntity.status(200).body(response);
    }


    //Read By ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentApiResponse<Student>> getStudentById(@PathVariable("id") Long studentId) {
        log.info("Received GET Request to getStudentsById: {}",studentId);
        boolean isExistById = studentService.existById(studentId);

        StudentApiResponse<Student> response;
        if(isExistById){
            Student student = studentService.getStudentById(studentId);
            log.info("student Exist studentId: {}",student);
            response = new StudentApiResponse<>(
                    "success",
                    200,
                    "Details found",
                    student
            );
        }else{
            log.info("student NOT EXIST studentId: {}",studentId);
            response = new StudentApiResponse<>(
                    "Error",
                    404,
                    "Details NOT found",
                    null
            );
        }
        log.info("Response - GET Request to getStudentsById: {}",response);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<StudentApiResponse<Student>> updateStudent(@PathVariable("id") Long studentId, @RequestBody Student student) {
        log.info("Received PUT Request to updateStudent: {}",student);
        log.info("Chek Id EXIST");
        boolean isExist  = studentService.existById(studentId);
        if (!isExist){
            log.info("studentId does Not exist StudentID: {}",studentId);
            StudentApiResponse<Student> response = new StudentApiResponse<>(
                    "Error",
                    404,
                    "Student Not Found",
                    null
            );
            log.info("Response - PUT Request to UpdateStudent- ID DOES NOT EXIST studentId: {}",studentId);
            return ResponseEntity.ok(response);
        }
        Student updatedStudent = studentService.updateStudent(studentId,student);
        log.info("Received a updatedStudent: {}",updatedStudent);
        StudentApiResponse<Student> response = new StudentApiResponse<>(
                "success",
                200,
                "Student updated sucessfully",
                updatedStudent
        );
        log.info("Response - PUT Request to updateStudent: {}",response);
        return ResponseEntity.ok(response);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<StudentApiResponse<String>> deleteStudent(@PathVariable("id") Long studentId) {
        log.info("Received DEL Request to Delete Student studentId: {}",studentId);
        boolean isDeleted = studentService.deleteStudentById(studentId);
        log.info("Student DELETE: {}",isDeleted);
        StudentApiResponse<String> response;
        if(isDeleted){
            response = new StudentApiResponse<>(
                    "Success",
                    200,
                    "Student Deleted Sucessfully!",
                    null
            );
            log.info("Response - DEL Request to Delete Student: {}",response);
        }else {
            response = new StudentApiResponse<>(
                    "error",
                    404,
                    "Student not found!",
                    null);
            log.error("Response - DEL Request to Delete Student: {} message: {}",response,response.getMessage());
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //DeleteAll
    @DeleteMapping
    public ResponseEntity<StudentApiResponse<String>> deleteAll(){
        log.info("Received DEL Request to DeleteAll");
        boolean isDeleted = studentService.deleteAll();
        StudentApiResponse<String> response;
        if (isDeleted){
            response = new StudentApiResponse<>(
                    "Success",
                    200,
                    "All Student Deleted Sucessfull!",
                    null
            );
            log.info("Response - DELAll Request to Delete Student: {}",response);
        }else {
            response = new StudentApiResponse<>(
                    "Error",
                    404,
                    "Student Not Found",
                    null
            );
            log.error("Response - DELETE ALL Request to Delete Student: {} message: {}",response,response.getMessage());
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
