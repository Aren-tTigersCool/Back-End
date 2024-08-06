package com.example.atc.domain.calorieRecord.dto;

import lombok.Data;

@Data
public class CalorieRecordDto {
    private double addSubCalorie;
    private double totalCalorie;
    private double todayTotalCalorie;
    private Long userId;
}
