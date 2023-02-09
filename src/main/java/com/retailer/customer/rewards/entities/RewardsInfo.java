package com.retailer.customer.rewards.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RewardsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMER_ID")
    private long customerId;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    @Column(name = "TRANSACTION_MONTH")
    private Integer transactionMonth;
    @Column(name = "TRANSACTION_YEAR")
    private Integer transactionYear;
    @Column(name = "TOTAL_REWARDS")
    private Integer totalRewards;
}
