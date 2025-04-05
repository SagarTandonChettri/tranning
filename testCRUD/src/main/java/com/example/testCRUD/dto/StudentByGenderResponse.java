package com.example.testCRUD.dto;

import com.example.testCRUD.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentByGenderResponse {

    private List<Student> maleStudent;
    private List<Student> femaleStudent;
}
