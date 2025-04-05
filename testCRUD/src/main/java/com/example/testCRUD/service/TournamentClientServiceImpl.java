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

    private static final String TOURNAMENT_API_URL = "http://localhost:8080/api/tournament/by-gender/";

    public List<TournamentDto>fetchTournamentByGender(String gender){
        String url = TOURNAMENT_API_URL + gender;
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
}