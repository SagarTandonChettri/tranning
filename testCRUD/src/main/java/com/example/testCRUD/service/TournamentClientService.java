package com.example.testCRUD.service;

import com.example.testCRUD.dto.TournamentDto;

import java.util.List;

public interface TournamentClientService {
    List<TournamentDto> fetchTournamentByGender(String gender);
    List<TournamentDto> getAllWinner ();
    TournamentDto getWinnerById(Long winnerId);
}
