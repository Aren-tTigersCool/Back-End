package com.example.atc.domain.co2Record.dto;

import lombok.Data;

@Data
public class Co2RecordDto {
    private double addSubCo2;
    private double totalCo2;
    private double todayTotalCo2;
    private Long userId;
}
