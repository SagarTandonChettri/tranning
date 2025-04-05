package com.example.testCRUD.dto;

import com.example.testCRUD.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherByGenderResponse {

    private List<Teacher> maleTeacher;
    private List<Teacher> femaleTeacher;

}
