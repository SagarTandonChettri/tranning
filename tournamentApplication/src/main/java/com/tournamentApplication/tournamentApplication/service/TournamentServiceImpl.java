package com.tournamentApplication.tournamentApplication.service;

import com.tournamentApplication.tournamentApplication.entity.Tournament;
import com.tournamentApplication.tournamentApplication.repository.TournamentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService{

    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    public Tournament saveStudent(Tournament tournament) {
        log.info("Received Request for saveTournament: {}",tournament);
        return tournamentRepository.save(tournament);
    }

    @Override
    public List<Tournament> fetchStudentList() {
        log.info("Received Fetching Request getTournametList");
        List<Tournament> tournaments = tournamentRepository.findAll();
        if (tournaments.isEmpty()) {
            log.warn("No Tournament found");
        } else {
            log.info("Successfully retrieved Tournament List: {}",tournaments);
        }
        return tournaments;
    }

    @Override
    public Tournament getTournamentById(Long tournamentId) {
        log.info("Received Request to GET tournament by tournamentId: {}",tournamentId);
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(()->{
            log.info("Detail NOT FOUND TournamentId: {}",tournamentId);
            return new RuntimeException("Tournament NOT FOUND ID: " + tournamentId);
        });
        log.info("Successfully GET Tournament - tournament: {}",tournament);
        return tournamentRepository.save(tournament);
    }

    @Override
    public List<Tournament> getTournamentBySportName(String sportName) {
        log.info("Received Fetching Request getTournament by sportName: {}", sportName);
        List<Tournament> tournaments = tournamentRepository.findBySportName(sportName);
        if (tournaments.isEmpty()) {
            log.warn("No sportName found with sportName: {}", sportName);
        } else {
            log.info("Successfully retrieved: {} tournament, with sportName: {}", tournaments.size(), sportName);
        }
        return tournaments;
    }
    //fetch top 2 highest-scoring per score
    @Override
    public List<Tournament> getTopWinners(String sportName) {
        log.info("GET 2 most higher scorer");
        return tournamentRepository.findBySportName(sportName)
                .stream()
                .sorted((t1, t2) -> Integer.compare(t2.getSportScore(), t1.getSportScore()))
                .limit(2)
                .collect(Collectors.toList());
    }

    @Override
    public Tournament updateStudent(Long tournamentId, Tournament newTournament) {
        log.info("Received Request to update tournament \n updateTournamentINFO[tournamentId: {},updatedTournament: {}], ",tournamentId,newTournament);

        Tournament oldTournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> {
                    log.info("tournamet not found with  TournamentId: {}",tournamentId);
                    return new RuntimeException("Tournament not found with ID: " + tournamentId);
                });

        oldTournament.setStudentName(newTournament.getStudentName());
        oldTournament.setAge(newTournament.getAge());
        oldTournament.setGender(newTournament.getGender());
        oldTournament.setSportName(newTournament.getSportName());
        oldTournament.setSportScore(newTournament.getSportScore());

        log.info("Successfully Update Tournament - Detail: {}",newTournament);
        return tournamentRepository.save(newTournament);
    }

    @Override
    public boolean deleteTournamentById(Long tournamentId) {
        log.info("Received Request for DELETE tournament tournamentId: {}",tournamentId);

        if(tournamentRepository.existsById(tournamentId)){
            log.info("tournament found tournamentId: {}",tournamentId);
            tournamentRepository.deleteById(tournamentId);
            return true;
        }else {
            log.warn("Tournament not found for Deletion tournamentId: {}",tournamentId);
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        log.info("Received a Request to delete All Entry");
        tournamentRepository.deleteAll();
        log.info("Request to DELETE ALL DATA complete");
        return true;
    }

    @Override
    public boolean existById(Long tournamentId) {
        return tournamentRepository.existsById(tournamentId);
    }

    @Override
    public List<Tournament> getTournamentsByGender(String gender) {
        return tournamentRepository.findByGender(gender);
    }
}
