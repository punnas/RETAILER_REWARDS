package com.retailer.customer.rewards.repositories;

import com.retailer.customer.rewards.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public List<Transaction>  findByCreatedDtBetween(Date oldDate, Date newDate);
}
