package com.tournamentApplication.tournamentApplication.service;

import com.tournamentApplication.tournamentApplication.entity.Tournament;

import java.util.List;

public interface TournamentService {

    //Save Operation
    Tournament saveStudent(Tournament tournament);

    //Read Operation
    //ALL
    List<Tournament> fetchStudentList();
    //ID
    Tournament getTournamentById(Long tournamentId);
    //SPORTS
    List<Tournament> getTournamentBySportName(String sportName);
    //Top Winner
    List<Tournament> getTopWinners(String sportName);

    //Update Operation
    Tournament updateStudent(Long tournamentId,Tournament tournament);

    ///Delete operation
    boolean deleteTournamentById(Long studentId);

    //DeleteAll operation
    boolean deleteAll();

    //exist by ID
    boolean existById(Long tournamentId);

    List<Tournament> getTournamentsByGender(String gender);
}
