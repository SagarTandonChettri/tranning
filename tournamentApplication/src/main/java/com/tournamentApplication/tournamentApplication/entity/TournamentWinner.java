package com.tournamentApplication.tournamentApplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentWinner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String StudentName;
    private int age;
    private String gender;
    private String sportName;
    private int sportScore;
}
