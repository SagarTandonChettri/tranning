package com.tournamentApplication.tournamentApplication.repository;


import com.tournamentApplication.tournamentApplication.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee,Long> {
}
