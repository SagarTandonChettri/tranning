package com.tournamentApplication.tournamentApplication.repository;

import com.tournamentApplication.tournamentApplication.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Long> {
    List<Tournament> findBySportName(String sportName);
    List<Tournament> findByGender(String gender);
}
