package com.example.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrderTO {
    private UUID orderId;
    private String customerName;
    private String orderDescription;
    private BigDecimal orderValue;
    private String status;
    private LocalDateTime orderDate;
}