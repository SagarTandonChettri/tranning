package com.example.testCRUD.service;

import com.example.testCRUD.entity.Student;

import java.util.List;

public interface StudentService {
    //Save Operation
    Student saveStudent(Student student);

    List<Student> getStudentByGender(String gender);

    //Read Operation
    Student getStudentById(Long studentId);

    List<Student> fetchStudentList();

    //Update Operation
    Student updateStudent(Long studentId,Student student);

    //Delete operation
    boolean deleteStudentById(Long studentId);

    //DeleteAll operation
    boolean deleteAll();

    //exist by ID
    boolean existById(Long studentId);
}
