package com.tournamentApplication.tournamentApplication.controller;

import com.tournamentApplication.tournamentApplication.dto.TournamentApiResponse;
import com.tournamentApplication.tournamentApplication.dto.TournamentGenderResponse;
import com.tournamentApplication.tournamentApplication.dto.TournamentWinnerResponse;
import com.tournamentApplication.tournamentApplication.entity.TournamentWinner;
import com.tournamentApplication.tournamentApplication.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tournament")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    //Post
    @PostMapping
    public ResponseEntity<TournamentApiResponse<TournamentWinner>> saveStudent(@RequestBody TournamentWinner tournamentWinner){
        log.info("Received POST request to save tournament:{}", tournamentWinner);
        TournamentWinner savedTournamentWinner = tournamentService.saveStudent(tournamentWinner);
        log.info("Saved Complete savedTournament: {}", savedTournamentWinner);
        TournamentApiResponse<TournamentWinner> response = new TournamentApiResponse<>(
                "success",
                201,
                "Tournament created successfully",
                savedTournamentWinner
        );
        log.info("Response - POST request to save Tournament: {}",response);
        return  ResponseEntity.status(201).body(response);
    }

    //Get
    @GetMapping
    public ResponseEntity<TournamentApiResponse<List<TournamentWinner>>> getAllTournament(){
        log.info("Received GET Request to getAllTournament");
        List<TournamentWinner> tournamentWinners = tournamentService.fetchStudentList();
        log.info("Received All tournament");
        TournamentApiResponse<List<TournamentWinner>> response = new TournamentApiResponse<>(
                "success",
                201,
                "Fetch complete",
                tournamentWinners
        );
        log.info("Response - GET Request to getAllTournament:{}",response);
        return ResponseEntity.ok(response);
    }

    //Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<TournamentApiResponse<TournamentWinner>> getTournamentById(@PathVariable("id") Long tournamentId){
        log.info("Received GET Request to getTournament by TournamentID:");
        boolean isExist = tournamentService.existById(tournamentId);

        TournamentApiResponse<TournamentWinner> response;
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
        TournamentWinner tournamentWinner = tournamentService.getTournamentById(tournamentId);
        log.info("Received a Tournament Detail TournamentDetail: {}, TournamentId: {}", tournamentWinner,tournamentId);
        response = new TournamentApiResponse<>(
                "Success",
                200,
                "Fetch Succesful",
                tournamentWinner
        );
        log.info("Response - GET Request to getTournamentById: {}",response);
        return ResponseEntity.ok(response);
    }

    //fetch data By sportName
    @GetMapping("/sports")
    public ResponseEntity<TournamentApiResponse<TournamentWinnerResponse>> getAllTournamentBySportName(){
        log.info("Received a Request to FETCH data getAllTournamentBySportName");

        List<TournamentWinner> cricketTournamentWinner = tournamentService.getTournamentBySportName("cricket");
        List<TournamentWinner> footballTournamentWinner = tournamentService.getTournamentBySportName("football");
        List<TournamentWinner> badmintonTournamentWinner = tournamentService.getTournamentBySportName("badminton");
        List<TournamentWinner> swimmingTournamentWinner = tournamentService.getTournamentBySportName("swimming");
        List<TournamentWinner> kabaddiTournamentWinner = tournamentService.getTournamentBySportName("kabaddi");

        log.info("Received Tournament List: {}", cricketTournamentWinner);
        log.info("Received Tournament List: {}", footballTournamentWinner);
        log.info("Received Tournament List: {}", badmintonTournamentWinner);
        log.info("Received Tournament List: {}", swimmingTournamentWinner);
        log.info("Received Tournament List: {}", kabaddiTournamentWinner);

        TournamentWinnerResponse sportNameWinnerResponse = new TournamentWinnerResponse(cricketTournamentWinner, footballTournamentWinner, badmintonTournamentWinner, swimmingTournamentWinner, kabaddiTournamentWinner);

        if(cricketTournamentWinner.isEmpty() && footballTournamentWinner.isEmpty() && badmintonTournamentWinner.isEmpty() && kabaddiTournamentWinner.isEmpty() && swimmingTournamentWinner.isEmpty()) {
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

        List<TournamentWinner> cricketTournamentWinner = tournamentService.getTopWinners("cricket");
        log.info("Received Tournament Winner List: {}", cricketTournamentWinner);

        List<TournamentWinner> footballTournamentWinner = tournamentService.getTopWinners("football");
        log.info("Received Tournament Winner List: {}", footballTournamentWinner);

        List<TournamentWinner> badmintonTournamentWinner = tournamentService.getTopWinners("badminton");
        log.info("Received Tournament Winner List: {}", badmintonTournamentWinner);

        List<TournamentWinner> swimmingTournamentWinner = tournamentService.getTopWinners("swimming");
        log.info("Received Tournament Winner List: {}", swimmingTournamentWinner);

        List<TournamentWinner> kabaddiTournamentWinner = tournamentService.getTopWinners("kabaddi");
        log.info("Received Tournament Winner List: {}", kabaddiTournamentWinner);

        TournamentWinnerResponse sportNameWinnerResponse = new TournamentWinnerResponse(cricketTournamentWinner, footballTournamentWinner, badmintonTournamentWinner, swimmingTournamentWinner, kabaddiTournamentWinner);

        if(cricketTournamentWinner.isEmpty() && footballTournamentWinner.isEmpty() && badmintonTournamentWinner.isEmpty() && swimmingTournamentWinner.isEmpty() && kabaddiTournamentWinner.isEmpty()) {
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

    //GET All Winner By Gender
    @GetMapping("/all-gender")
    public ResponseEntity<TournamentApiResponse<TournamentGenderResponse>> getAllWinnerBasedOnGender(){
        log.info("Fetching GET tournamentWinner Based on gender: MALE and FEMALE");

        List<TournamentWinner> maleWinner = tournamentService.getTournamentWinnersByGender("male");
        List<TournamentWinner> femaleWinner = tournamentService.getTournamentWinnersByGender("female");

        TournamentGenderResponse genderResponse = new TournamentGenderResponse(maleWinner,femaleWinner);

        log.info("Fetched List: {}",genderResponse);

        if(maleWinner.isEmpty() && femaleWinner.isEmpty()){
            TournamentApiResponse<TournamentGenderResponse> response = new TournamentApiResponse<>(
                    "error",
                    404,
                    "No Winner Founds",
                    null
            );
            log.info("Response - Request for FETCH getAllWinnerByGender NOT FOUND: {}",response);
            return ResponseEntity.status(404).body(response);
        }
        TournamentApiResponse<TournamentGenderResponse> response = new TournamentApiResponse<>(
                "success",
                200,
                "Details found",
                genderResponse
        );
        log.info("Response - Request for FETCH getAllWinnerByGender FOUND: {}",response);
        return ResponseEntity.status(200).body(response);
    }

    //Get Winner Based on Gender
    @GetMapping("/gender/{gender}")
    public ResponseEntity<TournamentApiResponse<List<TournamentWinner>>> getWinnerByGender(@PathVariable String gender){
        log.info("Fetching GET tournamentWinner Based on gender: {}", gender);
        List<TournamentWinner> tournamentWinners = tournamentService.getTournamentWinnersByGender(gender);
        log.info("List Received Based on Gender List: {}",tournamentWinners);

        if (tournamentWinners.isEmpty()){
            TournamentApiResponse<List<TournamentWinner>> response = new TournamentApiResponse<>(
                    "Error",
                    404,
                    "TournamentWinner Not Found",
                    null
            );
            log.info("Response - GET tournamentWinner Based on Gender: {}", gender);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        TournamentApiResponse<List<TournamentWinner>> response = new TournamentApiResponse<>(
                "Success",
                200,
                "Detail Found Based on Gender",
                tournamentWinners
        );
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<TournamentApiResponse<TournamentWinner>> updateTournament(@PathVariable("id") Long tournamentId, @RequestBody TournamentWinner tournamentWinner) {
        log.info("Received PUT Request to updateTournament: {}", tournamentWinner);
        log.info("Check Id EXIST");
        boolean isExist  = tournamentService.existById(tournamentId);
        if (!isExist){
            log.info("Tournament does Not exist TournamentId: {}",tournamentId);
            TournamentApiResponse<TournamentWinner> response = new TournamentApiResponse<>(
                    "Error",
                    404,
                    "TournamentWinner Not Found",
                    null
            );
            log.info("Response - PUT Request to UpdateTournament- ID DOES NOT EXIST TournamentId: {}",tournamentId);
            return ResponseEntity.status(404).build();
        }
        TournamentWinner updatedTournamentWinner = tournamentService.updateStudent(tournamentId, tournamentWinner);
        log.info("Received a updatedTournament: {}", updatedTournamentWinner);
        TournamentApiResponse<TournamentWinner> response = new TournamentApiResponse<>(
                "success",
                200,
                "Tournamentd updated sucessfully",
                updatedTournamentWinner
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
