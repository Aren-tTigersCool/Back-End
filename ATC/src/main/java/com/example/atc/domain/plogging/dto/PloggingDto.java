package com.example.atc.domain.plogging.dto;

import lombok.Data;

@Data
public class PloggingDto {
    private int timeTaken;
    private int authenticationTime;
    private String location;
    private int distance;
}
