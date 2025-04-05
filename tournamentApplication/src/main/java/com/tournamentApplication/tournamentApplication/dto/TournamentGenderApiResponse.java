package com.tournamentApplication.tournamentApplication.dto;


import com.tournamentApplication.tournamentApplication.entity.TournamentWinner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentGenderApiResponse {
    private List<TournamentWinner> maleWinner;
    private List<TournamentWinner> femaleWinner;
}
