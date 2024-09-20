package com.example.atc.domain.order.dto;

import lombok.Data;
import org.joda.time.DateTime;
@Data
public class OrderDto {

    private Long orderId;
    private Long price;
    private DateTime order_date;
}
