package com.example.testCRUD.service;

import com.example.testCRUD.dto.TournamentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TournamentClientServiceImpl implements TournamentClientService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String TOURNAMENT_API_GENDER_URL = "http://localhost:8080/api/tournament/gender/";
    private static final String TOURNAMENT_API_ALL_URL = "http://localhost:8080/api/tournament/";

    public List<TournamentDto>fetchTournamentByGender(String gender){
        String url = TOURNAMENT_API_GENDER_URL + gender;
        log.info("Fetching tournaments from URL: {}", url);
        try {
            ResponseEntity<List<TournamentDto>> response =
                    restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            }
            return Collections.emptyList();
        } catch (HttpClientErrorException e) {
            log.error("Tournament API returned 404: {}", e.getMessage());
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("Error fetching tournaments: {}", e.getMessage());
            throw new RuntimeException("Error fetching tournaments: " + e.getMessage());
        }
    }

    @Override
        public List<TournamentDto> getAllWinner() {
        String url = TOURNAMENT_API_ALL_URL + "all";
        log.info("Fetching tournamentWinner from URL: {}", url);
        try {
            ResponseEntity<List<TournamentDto>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});

            if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
                return response.getBody();
            }
            return Collections.emptyList();
        } catch (HttpClientErrorException e){
            log.error("Tournament API returned 404: {}", e.getMessage());
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("Error fetching tournaments: {}", e.getMessage());
            throw new RuntimeException("Error fetching tournaments: " + e.getMessage());
        }
    }

    @Override
    public TournamentDto getWinnerById(Long id) {
        String url = TOURNAMENT_API_ALL_URL + "id/" + id;
        log.info("Fetching tournamentWinner by ID from URL: {}", url);
        try {
            ResponseEntity<TournamentDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            }
            return null;
        } catch (HttpClientErrorException e) {
            log.error("Tournament API returned 404 for ID {}: {}", id, e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Error fetching tournament by ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error fetching tournament by ID: " + e.getMessage());
        }
    }


}