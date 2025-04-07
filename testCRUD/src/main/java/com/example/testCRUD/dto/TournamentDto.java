package com.example.testCRUD.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDto {
    private Long id;
    private String studentName;
    private int age;
    private String gender;
    private String sportName;
    private int sportScore;
}
