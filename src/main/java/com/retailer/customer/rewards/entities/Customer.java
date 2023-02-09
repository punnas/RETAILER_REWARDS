package com.retailer.customer.rewards.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Entity contains customer information
 * @author Suresh Punna
 */
@Entity
@Data
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_Id")
    private Long customerId;
    @Column(name = "customer_name")
    private String customerName;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_dt")
    private Date createdDt;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="customer")
    private Set<Transaction> transactions;
}
