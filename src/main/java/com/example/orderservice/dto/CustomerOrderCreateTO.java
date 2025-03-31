package com.example.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrderCreateTO {
    private String customerName;
    private String orderDescription;
    private BigDecimal orderValue;
    private String status;
    private LocalDateTime orderDate;
}