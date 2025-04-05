package com.tournamentApplication.tournamentApplication.service;

import com.tournamentApplication.tournamentApplication.entity.TournamentWinner;
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

    //POST Service
    @Override
    public TournamentWinner saveStudent(TournamentWinner tournamentWinner) {
        log.info("Received Request for saveTournament: {}", tournamentWinner);
        return tournamentRepository.save(tournamentWinner);
    }

    //GET Service
    @Override
    public List<TournamentWinner> fetchStudentList() {
        log.info("Received Fetching Request getTournametList");
        List<TournamentWinner> tournamentWinners = tournamentRepository.findAll();
        if (tournamentWinners.isEmpty()) {
            log.warn("No Tournament found");
        } else {
            log.info("Successfully retrieved Tournament List: {}", tournamentWinners);
        }
        return tournamentWinners;
    }

    @Override
    public TournamentWinner getTournamentById(Long tournamentId) {
        log.info("Received Request to GET tournament by tournamentId: {}",tournamentId);
        TournamentWinner tournamentWinner = tournamentRepository.findById(tournamentId).orElseThrow(()->{
            log.info("Detail NOT FOUND TournamentId: {}",tournamentId);
            return new RuntimeException("Tournament NOT FOUND ID: " + tournamentId);
        });
        log.info("Successfully GET Tournament - tournament: {}", tournamentWinner);
        return tournamentRepository.save(tournamentWinner);
    }

    @Override
    public List<TournamentWinner> getTournamentBySportName(String sportName) {
        log.info("Received Fetching Request getTournament by sportName: {}", sportName);
        List<TournamentWinner> tournamentWinners = tournamentRepository.findBySportName(sportName);
        if (tournamentWinners.isEmpty()) {
            log.warn("No sportName found with sportName: {}", sportName);
        } else {
            log.info("Successfully retrieved: {} tournament, with sportName: {}", tournamentWinners.size(), sportName);
        }
        return tournamentWinners;
    }
    //fetch top 2 highest-scoring per score
    @Override
    public List<TournamentWinner> getTopWinners(String sportName) {
        log.info("GET 2 most higher scorer");
        return tournamentRepository.findBySportName(sportName)
                .stream()
                .sorted((t1, t2) -> Integer.compare(t2.getSportScore(), t1.getSportScore()))
                .limit(2)
                .collect(Collectors.toList());
    }

    @Override
    public List<TournamentWinner> getTournamentWinnersByGender(String gender) {
        log.info("GET Request getTournamentsByGender: {}",gender);
        return tournamentRepository.findByGender(gender);
    }

    //Put Service
    @Override
    public TournamentWinner updateStudent(Long tournamentId, TournamentWinner newTournamentWinner) {
        log.info("Received Request to update tournament \n updateTournamentINFO[tournamentId: {},updatedTournament: {}], ",tournamentId, newTournamentWinner);

        TournamentWinner oldTournamentWinner = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> {
                    log.info("tournamet not found with  TournamentId: {}",tournamentId);
                    return new RuntimeException("Tournament not found with ID: " + tournamentId);
                });

        oldTournamentWinner.setStudentName(newTournamentWinner.getStudentName());
        oldTournamentWinner.setAge(newTournamentWinner.getAge());
        oldTournamentWinner.setGender(newTournamentWinner.getGender());
        oldTournamentWinner.setSportName(newTournamentWinner.getSportName());
        oldTournamentWinner.setSportScore(newTournamentWinner.getSportScore());

        log.info("Successfully Update Tournament - Detail: {}", newTournamentWinner);
        return tournamentRepository.save(newTournamentWinner);
    }


    //DEl Service
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

    //ID EXIST
    @Override
    public boolean existById(Long tournamentId) {
        return tournamentRepository.existsById(tournamentId);
    }
}
