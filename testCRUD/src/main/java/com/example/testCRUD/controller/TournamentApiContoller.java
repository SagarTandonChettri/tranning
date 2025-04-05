package com.example.testCRUD.controller;

import com.example.testCRUD.dto.TournamentDto;
import com.example.testCRUD.dto.TournamentDtoResponse;
import com.example.testCRUD.service.TournamentClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tournament")
public class TournamentApiContoller {

    @Autowired
    private TournamentClientServiceImpl tournamentClientService;

    @GetMapping("/by-gender/{gender}")
    public ResponseEntity<TournamentDtoResponse<List<TournamentDto>>> getTournamentsByGender(@PathVariable String gender) {
        List<TournamentDto> tournaments = tournamentClientService.fetchTournamentByGender(gender);

        if (tournaments.isEmpty()) {
            return ResponseEntity.status(404).body(new TournamentDtoResponse<>(
                    "error",
                    404,
                    "No tournaments found for gender: " + gender,
                    null));
        }

        return ResponseEntity.ok(new TournamentDtoResponse<>(
                "Success",
                200,
                "Tournaments found for gender: " + gender, tournaments));
    }

}
