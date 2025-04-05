package com.tournamentApplication.tournamentApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
@AllArgsConstructor
public class TournamentApiResponse<T> {
    private String status;
    private int statusCode;
    private String message;
    private T data;
}
