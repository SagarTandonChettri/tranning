package com.example.testCRUD.service;

import com.example.testCRUD.entity.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher saveTeacher(Teacher teacher);

    Teacher getTeacherById(Long teacherId);

    List<Teacher> fetchTeacherList();

    List<Teacher> fetchTeacherListByGender(String gender);

    Teacher updateTeacher(Long teacherId, Teacher teacher);

    Boolean deleteTeacherById(Long teacherId);

    Boolean existById(Long teacherId);
}
