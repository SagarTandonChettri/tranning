package com.tournamentApplication.tournamentApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentApiDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String gender;
}
