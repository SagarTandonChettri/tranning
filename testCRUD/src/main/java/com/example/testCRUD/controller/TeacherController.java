package com.example.testCRUD.controller;

import com.example.testCRUD.dto.StudentApiResponse;
import com.example.testCRUD.dto.TeacherApiReponse;
import com.example.testCRUD.dto.TeacherByGenderResponse;
import com.example.testCRUD.entity.Student;
import com.example.testCRUD.entity.Teacher;
import com.example.testCRUD.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //Post saveTeacher
    @PostMapping
    public ResponseEntity<TeacherApiReponse<Teacher>> saveTeacher(@RequestBody Teacher teacher){
        log.info("Received POST Request to Save Teacher: {}",teacher);

        Teacher savedTeacher = teacherService.saveTeacher(teacher);
        log.info("Saved Complete savedTeacher: {}",savedTeacher);
        TeacherApiReponse<Teacher> response =  new TeacherApiReponse<>(
                "success",
                201,
                "Teacher Created Successfully",
                savedTeacher
        );
        log.info("Response - Post Request to saveTeacher: {}",response);
        return ResponseEntity.status(201).body(response);
    }

    //Read
    @GetMapping
    public ResponseEntity<TeacherApiReponse<List<Teacher>>> getAllTeacher(){
        log.info("Received GET Request to getAllTeacher");
        List<Teacher> teachers = teacherService.fetchTeacherList();
        log.info("Received All Teacher");
        TeacherApiReponse<List<Teacher>> response = new TeacherApiReponse<>(
                "success",
                201,
                "Fetch complete",
                teachers
        );
        log.info("Response - GET Request to getAllTeacher:{}",response);
        return ResponseEntity.ok(response);
    }

    //Get By Teacher By Gender
    @GetMapping("/gender")
    public ResponseEntity<TeacherApiReponse<TeacherByGenderResponse>> getTeacherByGender(){
        log.info("Received GET Request to getTeacherByGender: Male & Female");
        List<Teacher> maleTeachers = teacherService.fetchTeacherListByGender("Male");
        List<Teacher> femaleTeachers = teacherService.fetchTeacherListByGender("Female");

        TeacherByGenderResponse genderRespoonse = new TeacherByGenderResponse(maleTeachers,femaleTeachers);

        if(femaleTeachers.isEmpty() && maleTeachers.isEmpty()){
            TeacherApiReponse<TeacherByGenderResponse> response = new TeacherApiReponse<>(
                    "error",
                    404,
                    "No Teacher Founds",
                    null
            );
            log.info("Response - Request for FETCH getAllTeacherByGender NOT FOUND: {}",response);
        }
        TeacherApiReponse<TeacherByGenderResponse> response = new TeacherApiReponse<>(
                "success",
                200,
                "Details found",
                genderRespoonse
        );
        log.info("Response - Request for FETCH getAllTeacherByGender: {}",response);
        return ResponseEntity.status(200).body(response);
    }

    //Read By ID
    @GetMapping("/{id}")
    public ResponseEntity<TeacherApiReponse<Teacher>> getTeacherById(@PathVariable("id") Long teacherId) {
        log.info("Received GET Request to getTeacherById: {}",teacherId);
        boolean isExist = teacherService.existById(teacherId);
        TeacherApiReponse<Teacher> response;
        if (isExist){
            Teacher teacher = teacherService.getTeacherById(teacherId);
            log.info("Received a teacher from given teacherId: {}",teacherId);
            response = new TeacherApiReponse<>(
                    "success",
                    200,
                    "Details found",
                    teacher
            );
        }else {
            log.info("teacher NOT EXIST teacherId: {}",teacherId);
            response = new TeacherApiReponse<>(
                    "Error",
                    404,
                    "Details NOT found",
                    null
            );
        }

        log.info("Response - GET Request to getTeacherById: {}",teacherId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<TeacherApiReponse<Teacher>> updateTeacher(@PathVariable("id") Long teacherId, @RequestBody Teacher teacher) {
        log.info("Received PUT Request to updateTeacher: {}",teacher);

        log.info("check Id EXIST");
        boolean isExist  = teacherService.existById(teacherId);
        if (!isExist){
            log.info("teacherId does Not exist teacherID: {}",teacherId);
            TeacherApiReponse<Teacher> response = new TeacherApiReponse<>(
                    "Error",
                    404,
                    "Teacher Not Found",
                    null
            );
            log.info("Response - PUT Request to UpdateTeacher- ID DOES NOT EXIST teacherId: {}",teacherId);
            return ResponseEntity.ok(response);
        }
        Teacher updatedTeacher = teacherService.updateTeacher(teacherId,teacher);
        log.info("Received a updateTeacher: {}",updatedTeacher);
        TeacherApiReponse<Teacher> response = new TeacherApiReponse<>(
                "success",
                200,
                "Teacher updated sucessfully",
                updatedTeacher
        );
        log.info("Response - PUT Request to updatedTeacher: {}",response);
        return ResponseEntity.ok(response);
    }


    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<TeacherApiReponse<String>> deleteTeacher(@PathVariable("id") Long teacherId) {
        log.info("Received DEL Request to Delete Teacher teacherId: {}",teacherId);
        boolean isDeleted = teacherService.deleteTeacherById(teacherId);
        log.info("teacher DELETE: {}",isDeleted);
        TeacherApiReponse<String> response;
        if(isDeleted){
            response = new TeacherApiReponse<>(
                    "Success",
                    200,
                    "Teacher Deleted Sucessfully!",
                    null
            );
            log.info("Response - DEL Request to Delete teacher: {}",response);
        }else {
            response = new TeacherApiReponse<>(
                    "error",
                    404,
                    "Teacher not found!",
                    null);
            log.error("Response - DEL Request to Delete teacher: {} message: {}",response,response.getMessage());
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
