package com.retailer.customer.rewards.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private Long customerId;
    private BigDecimal amount;
}
