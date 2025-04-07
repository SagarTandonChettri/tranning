package com.tournamentApplication.tournamentApplication.controller;

import com.tournamentApplication.tournamentApplication.dto.TournamentApiResponse;
import com.tournamentApplication.tournamentApplication.entity.TournamentWinner;
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
public class TournamentExposureController {

    @Autowired
    private TournamentService tournamentService;

    //Receiving from another api CALLING
    //API - GET DATA BY GENDER Sending
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<TournamentWinner>> getTournamentsByGender(@PathVariable String gender) {
        log.info("Fetching tournaments for gender: {}", gender);
        List<TournamentWinner> tournamentWinners = tournamentService.getTournamentWinnersByGender(gender);

        if (tournamentWinners.isEmpty()) {
            log.warn("No tournaments found for gender: {}", gender);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        log.info("Returning {} tournaments for gender: {}", tournamentWinners.size(), gender);
        return ResponseEntity.ok(tournamentWinners);
    }

    //API - GET DATA BY All
    @GetMapping("/all")
    public ResponseEntity<List<TournamentWinner>> getAllTournamentWinners(){
        log.info("Received GET REQUEST getTournamentWinners");
        List<TournamentWinner> tournamentWinners = tournamentService.fetchStudentList();
        log.info("Received fetch list: {}",tournamentWinners);
        if(tournamentWinners.isEmpty()){
            log.info("List is Empty");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        log.info("total Entry {}, tournamentWinner: {}",tournamentWinners.size(),tournamentWinners);
        return ResponseEntity.ok(tournamentWinners);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TournamentApiResponse<TournamentWinner>> getTournamentById(@PathVariable("id") Long winnerId){
        log.info("Received GET REQUEST get TournamentWinner by ID: {}",winnerId);
        boolean isExist  = tournamentService.existById(winnerId);
        log.info("ID exist: {}",isExist);
        if(!isExist){
            log.info("Tournament does Not exist winnerId: {}",winnerId);
            TournamentApiResponse<TournamentWinner> response = new TournamentApiResponse<>(
                    "Error",
                    404,
                    "Tournament Winner Not Found",
                    null
            );
            log.info("Response - PUT Request to UpdateTournament- ID DOES NOT EXIST winnerId: {}",winnerId);
            return ResponseEntity.status(404).body(response);
        }
        TournamentWinner tournamentWinnerById = tournamentService.getTournamentById(winnerId);
        log.info("Received Entry-ID: {},ENTRY: {}", winnerId,tournamentWinnerById);
        TournamentApiResponse<TournamentWinner> response = new TournamentApiResponse<>(
                "success",
                200,
                "Tournamentd FOUND Sucessfully",
                tournamentWinnerById
        );
        log.info("Response - PUT Request to updatedTournament: {}",response);
        return ResponseEntity.status(200).body(response);
    }

}
