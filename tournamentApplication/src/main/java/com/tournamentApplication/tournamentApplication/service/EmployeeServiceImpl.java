package com.tournamentApplication.tournamentApplication.service;

import com.tournamentApplication.tournamentApplication.entity.Employee;
import com.tournamentApplication.tournamentApplication.repository.EmployeeRepository;
import reactor.core.publisher.Mono;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Mono<Employee> getEmployeeById(Long id) {
        return  employeeRepository.findById(id);
    }

    @Override
    public Mono<Boolean> existById(Long tournamentId) {
        return employeeRepository.existsById(tournamentId);
    }
}
