package com.retailer.customer.rewards.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity contains transaction info made by customer
 * @author Suresh Punna
 */
@Entity
@Data
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name="customer_Id", nullable = false)
    private Customer customer;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_id", referencedColumnName = "reward_id")
    private Reward reward;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_dt")
    private Date createdDt;

}
