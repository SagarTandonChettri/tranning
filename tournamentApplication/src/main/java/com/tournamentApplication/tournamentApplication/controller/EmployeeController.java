package com.tournamentApplication.tournamentApplication.controller;

import com.tournamentApplication.tournamentApplication.dto.TournamentApiResponse;
import com.tournamentApplication.tournamentApplication.entity.Employee;
import com.tournamentApplication.tournamentApplication.entity.TournamentWinner;
import com.tournamentApplication.tournamentApplication.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TournamentApiResponse<Employee>>> getEmployeeById(@PathVariable("id")Long employeeId){
        return employeeService.existById(employeeId)
                .flatMap(exists -> {
                    if (!exists) {
                        TournamentApiResponse<Employee> response = new TournamentApiResponse<>(
                                "Error",
                                404,
                                "EmployeeID Not Found",
                                null
                        );
                        return Mono.just(ResponseEntity.status(404).body(response));
                    } else {
                        return employeeService.getEmployeeById(employeeId)
                                .map(employee -> {
                                    TournamentApiResponse<Employee> response = new TournamentApiResponse<>(
                                            "Success",
                                            200,
                                            "Fetch Successful",
                                            employee
                                    );
                                    log.info("Response - GET Request to getEmployeeById: {}", response);
                                    return ResponseEntity.ok(response);
                                });
                    }
                });
    }
}
