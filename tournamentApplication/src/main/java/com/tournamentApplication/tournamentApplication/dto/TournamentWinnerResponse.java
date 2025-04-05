package com.tournamentApplication.tournamentApplication.dto;

import com.tournamentApplication.tournamentApplication.entity.TournamentWinner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentWinnerResponse {
    private List<TournamentWinner> cricketWinner;
    private List<TournamentWinner> footballWinner;
    private List<TournamentWinner> badmintonWinner;
    private List<TournamentWinner> swimmingWinner;
    private List<TournamentWinner> kabaddiWinner;
}
