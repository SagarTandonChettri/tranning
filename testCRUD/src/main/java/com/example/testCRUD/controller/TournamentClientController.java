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
public class TournamentClientController {

    @Autowired
    private TournamentClientServiceImpl tournamentClientService;

    @GetMapping("/gender/{gender}")
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

    @GetMapping("/all")
    public ResponseEntity<TournamentDtoResponse<List<TournamentDto>>> getTournamentWinnerAll() {
        List<TournamentDto> tournaments = tournamentClientService.getAllWinner();
        if (tournaments.isEmpty()) {
            return ResponseEntity.status(404).body(new TournamentDtoResponse<>(
                    "error",
                    404,
                    "No tournaments found",
                    null));
        }

        return ResponseEntity.ok(new TournamentDtoResponse<>(
                "success",
                200,
                "Tournaments fetched successfully",
                tournaments));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TournamentDtoResponse<TournamentDto>> getTournamentWinnerById(@PathVariable("id") Long winnerId) {
        log.info("Received request for getTournamentWinnerById with ID: {}", winnerId);

        TournamentDto tournament = tournamentClientService.getWinnerById(winnerId);
        log.info("Detail Received: {}",tournament);
        if (tournament == null) {
            return ResponseEntity.status(404).body(new TournamentDtoResponse<>(
                    "error",
                    404,
                    "Tournament not found for ID: " + winnerId,
                    null
            ));
        }
        return ResponseEntity.status(200).body(new TournamentDtoResponse<>(
                "success",
                200,
                "Tournament found",
                tournament
        ));
    }

}
