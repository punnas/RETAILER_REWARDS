package com.retailer.customer.rewards.repositories;

import com.retailer.customer.rewards.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository <Customer, Long> {
}
