package com.tournamentApplication.tournamentApplication.service;

import com.tournamentApplication.tournamentApplication.entity.Employee;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Mono<Employee> getEmployeeById(Long id);
    //exist by ID
    Mono<Boolean> existById(Long tournamentId);
}
