package com.example.testCRUD.repository;

import com.example.testCRUD.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    List<Teacher> findByGender(String gender);
}
