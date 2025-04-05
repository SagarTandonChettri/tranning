package com.tournamentApplication.tournamentApplication.repository;

import com.tournamentApplication.tournamentApplication.entity.TournamentWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentWinner,Long> {
    List<TournamentWinner> findBySportName(String sportName);
    List<TournamentWinner> findByGender(String gender);
}
