package com.example.testCRUD.service;

import com.example.testCRUD.dto.TeacherApiReponse;
import com.example.testCRUD.entity.Teacher;
import com.example.testCRUD.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Boolean existById(Long teacherId) {
        log.info("Received Request ID Exist teacherId: {}",teacherId);
        return teacherRepository.existsById(teacherId);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        log.info("Received Request for saveTeacher: {}",teacher);
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getTeacherById(Long teacherId) {
        log.info("Received Request for getTeacherById: {}",teacherId);

        //getTeacherById calling findById method and stored it in --> teacher
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(()->{

            //if teacher id DO NOT EXIST throw Exception
            log.info("Teacher not found with TeacherId: {}",teacherId);
            return new RuntimeException("Teacher not found with id: " + teacherId);
        });

        //if teacher id DOES EXIST return student
        log.info("Successfully Retrieved Teacher: {}",teacher);
        return  teacher;
    }

    @Override
    public List<Teacher> fetchTeacherList() {
        log.info("Received Request for fetchStudentList");
        return (List<Teacher>) teacherRepository.findAll();
    }

    @Override
    public List<Teacher> fetchTeacherListByGender(String gender) {
        log.info("Received Request For fetchTeacherListByGender: {}",gender);
        List<Teacher> teachers = teacherRepository.findByGender(gender);
        if (teachers.isEmpty()){
            log.warn("No students found with gender: {}", gender);
        }else {
            log.info("Successfully retrieved {} student(s) with gender: {}", teachers.size(), gender);

        }
        return teachers;
    }

    @Override
    public Teacher updateTeacher(Long teacherId, Teacher updatedTeacher) {
        log.info("Received Request to update teacher \n updateTeacherINFO[teacherId: {},updatedTeacher: {}], ",teacherId,updatedTeacher);

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> {
                    log.info("teacher not found with  teacherId: {}",teacherId);
                    return new RuntimeException("teacher not found with ID: " + teacherId);
                });

        teacher.setFirstName(updatedTeacher.getFirstName());
        teacher.setLastName(updatedTeacher.getLastName());
        teacher.setGender(updatedTeacher.getGender());
        teacher.setSpecialityOn(updatedTeacher.getSpecialityOn());

        log.info("Successfully Update Teacher - teacher: {}",teacher);
        return teacherRepository.save(teacher);
    }

    @Override
    public Boolean deleteTeacherById(Long teacherId) {
        log.info("Received Request for DELETE teacher teacherId: {}",teacherId);

        if(teacherRepository.existsById(teacherId)){
            log.info("Student found teacher: {}",teacherId);
            teacherRepository.deleteById(teacherId);
            return true;
        }else {
            log.info("teacher not found for Deletion teacherId: {}",teacherId);
            return false;
        }
    }
}
