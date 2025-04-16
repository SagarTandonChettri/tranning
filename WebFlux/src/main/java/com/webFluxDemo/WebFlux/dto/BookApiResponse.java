package com.webFluxDemo.WebFlux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BookApiResponse<T> {
    private String status;
    private int statusCode;
    private String message;
    private T data;
}
