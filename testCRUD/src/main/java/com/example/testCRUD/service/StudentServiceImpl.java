package com.example.testCRUD.service;

import com.example.testCRUD.entity.Student;
import com.example.testCRUD.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;



    //LOGGER INSTANCE BUT WE USING SLF4J lomboak @SLF4J annotation
    //private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    //Exist by id
    @Override
    public boolean existById(Long studentId) {
        return studentRepository.existsById(studentId);
    }


    //Save operation
    @Override
    public Student saveStudent(Student student) {
        log.info("Received Request for saveStudent: {}",student);
        return studentRepository.save(student);
    }

    //Read Operation
    @Override
    public List<Student> fetchStudentList() {
        log.info("Received Request for fetchStudentList");
        return (List<Student>) studentRepository.findAll();
    }


    //Fetch based on Gender
    @Override
    public List<Student> getStudentByGender(String gender) {

        log.info("Received Fetching Request getStudentByGender GENDER: {}", gender);
        List<Student> students = studentRepository.findByGender(gender);
        if (students.isEmpty()) {
            log.warn("No students found with gender: {}", gender);
        } else {
            log.info("Successfully retrieved {} student(s) with gender: {}", students.size(), gender);
        }
        return students;
    }

//    Get STUDENT by ID
    @Override
    public Student getStudentById(Long studentId) {
        //entered student service getStudentById with request:: studentId
        log.info("Received Request for getStudentById: {}",studentId);

        //getStudentByID calling findById method and stored it in --> student
        Student student = studentRepository.findById(studentId).orElseThrow(()->{

            //if student id DO NOT EXIST throw Exception
            log.info("Student not found with studentId: {}",studentId);
            return new RuntimeException("Student not found with id: " + studentId);
        });

        //if student id EXIST return student
        log.info("Successfully Retrieved Student: {}",student);
        return  student;

        //return studentRepository.findById(studentId)
            //.orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
    }


    //get data by gender
//    public List<Student> getStudentByGender(String gender){
//    }

    //Update Operation
    @Override
    public Student updateStudent(Long studentId, Student updatedStudent) {

        log.info("Received Request to update Student \n updateStudentINFO[studentId: {},updatedStudent: {}], ",updatedStudent,updatedStudent);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.info("Student not found with  studentId: {}",studentId);
                    return new RuntimeException("Student not found with ID: " + studentId);
                });

        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setGender(updatedStudent.getGender());

        log.info("Successfully Update Student - student: {}",student);
        return studentRepository.save(student);
    }


    //Delete Operation
    @Override
    public boolean deleteStudentById(Long studentId) {

        log.info("Received Request for DELETE Student studentId: {}",studentId);

        if(studentRepository.existsById(studentId)){
            log.info("Student found studentId: {}",studentId);
            studentRepository.deleteById(studentId);
            return true;
        }else {
            log.warn("Student not found for Deletion studentId: {}",studentId);
            return false;
        }
    }

    //Delete All Operation
    @Override
    public boolean deleteAll() {
        log.info("Received a Request to delete All Entry");
        studentRepository.deleteAll();
        log.info("Request to DELETE ALL DATA complete");
        return true;
    }

}
