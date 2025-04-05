package com.tournamentApplication.tournamentApplication.controller;

import com.tournamentApplication.tournamentApplication.dto.TournamentApiResponse;
import com.tournamentApplication.tournamentApplication.dto.TournamentWinnerResponse;
import com.tournamentApplication.tournamentApplication.entity.Tournament;
import com.tournamentApplication.tournamentApplication.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tournament")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    //Post
    @PostMapping
    public ResponseEntity<TournamentApiResponse<Tournament>> saveStudent(@RequestBody Tournament tournament){
        log.info("Received POST request to save tournament:{}",tournament);
        Tournament savedTournament = tournamentService.saveStudent(tournament);
        log.info("Saved Complete savedTournament: {}",savedTournament);
        TournamentApiResponse<Tournament> response = new TournamentApiResponse<>(
                "success",
                201,
                "Tournament created successfully",
                savedTournament
        );
        log.info("Response - POST request to save Tournament: {}",response);
        return  ResponseEntity.status(201).body(response);
    }

    //Get
    @GetMapping
    public ResponseEntity<TournamentApiResponse<List<Tournament>>> getAllTournament(){
        log.info("Received GET Request to getAllTournament");
        List<Tournament> tournaments = tournamentService.fetchStudentList();
        log.info("Received All tournament");
        TournamentApiResponse<List<Tournament>> response = new TournamentApiResponse<>(
                "success",
                201,
                "Fetch complete",
                tournaments
        );
        log.info("Response - GET Request to getAllTournament:{}",response);
        return ResponseEntity.ok(response);
    }

    //Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<TournamentApiResponse<Tournament>> getTournamentById(@PathVariable("id") Long tournamentId){
        log.info("Received GET Request to getTournament by TournamentID:");
        boolean isExist = tournamentService.existById(tournamentId);

        TournamentApiResponse<Tournament> response;
        if (!isExist){
            log.info("Tournament does NOT EXIST TournamentId: {}",tournamentId);
            response = new TournamentApiResponse<>(
                    "Error",
                    404,
                    "TournamentID Not Found",
                    null
            );
            log.info("Response - GET Request to getTournament by TournamentID: {}",tournamentId);
            return ResponseEntity.ok(response);
        }
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        log.info("Received a Tournament Detail TournamentDetail: {}, TournamentId: {}",tournament,tournamentId);
        response = new TournamentApiResponse<>(
                "Success",
                200,
                "Fetch Succesful",
                tournament
        );
        log.info("Response - GET Request to getTournamentById: {}",response);
        return ResponseEntity.ok(response);
    }

    //fetch data By sportName
    @GetMapping("/sports")
    public ResponseEntity<TournamentApiResponse<TournamentWinnerResponse>> getAllTournamentBySportName(){
        log.info("Received a Request to FETCH data getAllTournamentBySportName");

        List<Tournament> cricketTournament = tournamentService.getTournamentBySportName("cricket");
        List<Tournament> footballTournament = tournamentService.getTournamentBySportName("football");
        List<Tournament> tennisTournament = tournamentService.getTournamentBySportName("tennis");
        List<Tournament> hockeyTournament = tournamentService.getTournamentBySportName("hockey");
        List<Tournament> volleyballTournament = tournamentService.getTournamentBySportName("volleyball");

        log.info("Received Tournament List: {}",cricketTournament);
        log.info("Received Tournament List: {}",footballTournament);
        log.info("Received Tournament List: {}",tennisTournament);
        log.info("Received Tournament List: {}",hockeyTournament);
        log.info("Received Tournament List: {}",volleyballTournament);

        TournamentWinnerResponse sportNameWinnerResponse = new TournamentWinnerResponse(cricketTournament,footballTournament,tennisTournament,hockeyTournament,volleyballTournament);

        if(cricketTournament.isEmpty() && footballTournament.isEmpty() && tennisTournament.isEmpty() && hockeyTournament.isEmpty() && volleyballTournament.isEmpty()) {
            TournamentApiResponse<TournamentWinnerResponse> response = new TournamentApiResponse<>(
                    "error",
                    404,
                    "Not found",
                    null
            );
            log.info("Response - Request for FETCH NO Tournament FOUND: {}",response);
            return ResponseEntity.status(404).body(response);
        }
        TournamentApiResponse<TournamentWinnerResponse> response = new TournamentApiResponse<>(
                "Success",
                200,
                "Found Based On SportName",
                sportNameWinnerResponse
        );
        log.info("Response - Request for FETCH getAllTournamentBySportName STUDENT FOUND: {}",response);
        return ResponseEntity.status(200).body(response);
    }

    //Fetch data by Top winner
    @GetMapping("/winner")
    public ResponseEntity<TournamentApiResponse<TournamentWinnerResponse>> getAllWinner(){
        log.info("Received a Request to FETCH Winner data Based on sportName");

        List<Tournament> cricketTournament = tournamentService.getTopWinners("cricket");
        log.info("Received Tournament Winner List: {}",cricketTournament);

        List<Tournament> footballTournament = tournamentService.getTopWinners("football");
        log.info("Received Tournament Winner List: {}",footballTournament);

        List<Tournament> tennisTournament = tournamentService.getTopWinners("tennis");
        log.info("Received Tournament Winner List: {}",tennisTournament);

        List<Tournament> hockeyTournament = tournamentService.getTopWinners("hockey");
        log.info("Received Tournament Winner List: {}",hockeyTournament);

        List<Tournament> volleyballTournament = tournamentService.getTopWinners("volleyball");
        log.info("Received Tournament Winner List: {}",volleyballTournament);

        TournamentWinnerResponse sportNameWinnerResponse = new TournamentWinnerResponse(cricketTournament,footballTournament,tennisTournament,hockeyTournament,volleyballTournament);

        if(cricketTournament.isEmpty() && footballTournament.isEmpty() && tennisTournament.isEmpty() && hockeyTournament.isEmpty() && volleyballTournament.isEmpty()) {
            TournamentApiResponse<TournamentWinnerResponse> response = new TournamentApiResponse<>(
                    "error",
                    404,
                    "No Winner found",
                    null
            );
            log.info("Response - Request for FETCH Winner NO Tournament FOUND: {}",response);
            return ResponseEntity.status(404).body(response);
        }
        TournamentApiResponse<TournamentWinnerResponse> response = new TournamentApiResponse<>(
                "Success",
                200,
                "Winners Found Based On SportName",
                sportNameWinnerResponse
        );
        log.info("Response - Request for FETCH All winner STUDENT FOUND: {}",response);
        return ResponseEntity.status(200).body(response);
    }


    // Update
    @PutMapping("/{id}")
    public ResponseEntity<TournamentApiResponse<Tournament>> updateTournament(@PathVariable("id") Long tournamentId, @RequestBody Tournament tournament) {
        log.info("Received PUT Request to updateTournament: {}",tournament);
        log.info("Check Id EXIST");
        boolean isExist  = tournamentService.existById(tournamentId);
        if (!isExist){
            log.info("Tournament does Not exist TournamentId: {}",tournamentId);
            TournamentApiResponse<Tournament> response = new TournamentApiResponse<>(
                    "Error",
                    404,
                    "Tournament Not Found",
                    null
            );
            log.info("Response - PUT Request to UpdateTournament- ID DOES NOT EXIST TournamentId: {}",tournamentId);
            return ResponseEntity.ok(response);
        }
        Tournament updatedTournament = tournamentService.updateStudent(tournamentId,tournament);
        log.info("Received a updatedTournament: {}",updatedTournament);
        TournamentApiResponse<Tournament> response = new TournamentApiResponse<>(
                "success",
                200,
                "Tournamentd updated sucessfully",
                updatedTournament
        );
        log.info("Response - PUT Request to updatedTournament: {}",response);
        return ResponseEntity.ok(response);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<TournamentApiResponse<String>> deleteStudent(@PathVariable("id") Long tournamentId) {
        log.info("Received DEL Request to Delete Tournament tournamentId: {}",tournamentId);
        boolean isDeleted = tournamentService.deleteTournamentById(tournamentId);
        log.info("Student DELETE: {}",isDeleted);
        TournamentApiResponse<String> response;
        if(isDeleted){
            response = new TournamentApiResponse<>(
                    "Success",
                    200,
                    "Tournament Deleted Sucessfully!",
                    null
            );
            log.info("Response - DEL Request to Delete tournament: {}",response);
        }else {
            response = new TournamentApiResponse<>(
                    "error",
                    404,
                    "Tournament not found!",
                    null);
            log.error("Response - DEL Request to Delete tournament: {} message: {}",response,response.getMessage());
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //DeleteAll
    @DeleteMapping("/deleteAll")
    public ResponseEntity<TournamentApiResponse<String>> deleteAll(){
        log.info("Received DEL Request to DeleteAll");
        boolean isDeleted = tournamentService.deleteAll();
        TournamentApiResponse<String> response;
        if (isDeleted){
            response = new TournamentApiResponse<>(
                    "Success",
                    200,
                    "All Deleted Sucessfull!",
                    null
            );
            log.info("Response - DELETE All Request to Delete Student: {}",response);
        }else {
            response = new TournamentApiResponse<>(
                    "Error",
                    404,
                    "Tournament Not Found",
                    null
            );
            log.error("Response - DELETE ALL Request to Delete Tournament: {} message: {}",response,response.getMessage());
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //

}
