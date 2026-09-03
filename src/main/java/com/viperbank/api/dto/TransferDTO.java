package com.viperbank.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDTO {
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
    private String description;
}
