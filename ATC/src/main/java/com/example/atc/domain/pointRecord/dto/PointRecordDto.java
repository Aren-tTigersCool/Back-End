package com.example.atc.domain.pointRecord.dto;

import lombok.Data;

@Data
public class PointRecordDto {
    private int addSubPoint;
    private int totalPoint;
    private int todayTotalPoint;
    private Long userId;
}
