package com.tournamentApplication.tournamentApplication.service;

import com.tournamentApplication.tournamentApplication.entity.TournamentWinner;

import java.util.List;

public interface TournamentService {

    //Save Operation
    TournamentWinner saveStudent(TournamentWinner tournamentWinner);

    //Read Operation
    //ALL
    List<TournamentWinner> fetchStudentList();
    //ID
    TournamentWinner getTournamentById(Long tournamentId);
    //SPORTS
    List<TournamentWinner> getTournamentBySportName(String sportName);
    //Top Winner
    List<TournamentWinner> getTopWinners(String sportName);
    //All list Based on Gender
    List<TournamentWinner> getWinnerByGender(String gender);

    //Update Operation
    TournamentWinner updateStudent(Long tournamentId, TournamentWinner tournamentWinner);

    ///Delete operation
    boolean deleteTournamentById(Long studentId);

    //DeleteAll operation
    boolean deleteAll();

    //exist by ID
    boolean existById(Long tournamentId);

    List<TournamentWinner> getTournamentsByGender(String gender);
}
