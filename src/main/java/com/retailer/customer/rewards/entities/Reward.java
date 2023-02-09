package com.retailer.customer.rewards.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity contains rewards awarded for each transaction for a customer.
 * @author Suresh Punna
 */
@Entity
@Data
@Table(name = "reward")
public class Reward {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id", nullable = false)
    private Long rewardId;

    @OneToOne(mappedBy = "reward")
    private Transaction transactionId;

    @Column(name = "rewards")
    private BigDecimal rewards;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_dt")
    private Date createdDt;

}
