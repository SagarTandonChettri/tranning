package com.tournamentApplication.tournamentApplication.controller;

import com.tournamentApplication.tournamentApplication.entity.Tournament;
import com.tournamentApplication.tournamentApplication.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tournament")
public class TournamentApiController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping("/by-gender/{gender}")
    public ResponseEntity<List<Tournament>> getTournamentsByGender(@PathVariable String gender) {
        log.info("Fetching tournaments for gender: {}", gender);
        List<Tournament> tournaments = tournamentService.getTournamentsByGender(gender);

        if (tournaments.isEmpty()) {
            log.warn("No tournaments found for gender: {}", gender);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        log.info("Returning {} tournaments for gender: {}", tournaments.size(), gender);
        return ResponseEntity.ok(tournaments);
    }
}
