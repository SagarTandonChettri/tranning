package com.tournamentApplication.tournamentApplication.service;

import com.tournamentApplication.tournamentApplication.config.AppConfig;
import com.tournamentApplication.tournamentApplication.dto.StudentApiDto;
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
public class StudentApiServiceImpl implements StudentApiService{

    @Autowired
    private RestTemplate restTemplate;

    private static final String STUDENT_APP_URL = "http://localhost:8081/api/student/";

    @Override
    public List<StudentApiDto> getAllStudent() {
        String url = STUDENT_APP_URL +"all";
        log.info("Received a Request to getAllStudent From URL: {}",url);
        try{
            ResponseEntity<List<StudentApiDto>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
                return response.getBody();
            }
            return Collections.emptyList();
        }catch (HttpClientErrorException e){
            log.error("Tournament API returned 404: {}", e.getMessage());
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("Error fetching tournaments: {}", e.getMessage());
            throw new RuntimeException("Error fetching tournaments: " + e.getMessage());
        }
    }
}
