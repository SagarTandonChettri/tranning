package com.tournamentApplication.tournamentApplication.dto;

import com.tournamentApplication.tournamentApplication.entity.Tournament;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentWinnerResponse {
    private List<Tournament> cricketWinner;
    private List<Tournament> footballWinner;
    private List<Tournament> tennisWinner;
    private List<Tournament> hockeyWinner;
    private List<Tournament> vollyBallWinner;
}
