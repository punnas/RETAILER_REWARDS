package com.retailer.customer.rewards.services;

import com.retailer.customer.rewards.repositories.RewardsRepository;
import com.retailer.customer.rewards.dtos.CustomerDto;
import com.retailer.customer.rewards.dtos.TransactionDto;
import com.retailer.customer.rewards.entities.Customer;
import com.retailer.customer.rewards.entities.Reward;
import com.retailer.customer.rewards.entities.RewardsInfo;
import com.retailer.customer.rewards.entities.Transaction;
import com.retailer.customer.rewards.exceptions.ApplicationException;
import com.retailer.customer.rewards.exceptions.CustomerNotFoundException;
import com.retailer.customer.rewards.repositories.CustomerRepository;
import com.retailer.customer.rewards.repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * This class is to handle Customer Rewards related business methods
 * @author Suresh Punna
 */
@Service
@Slf4j
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RewardsRepository rewardsRepository;

    /**
     * Save Customer Information
     * @param customerDto
     */
    @Transactional
    public boolean saveCustomer(CustomerDto customerDto) {
        if (customerDto == null || customerDto.getName() == null || customerDto.getName().isBlank()) {
            log.error("Name value is missing");
            throw new ApplicationException("Name value is missing");
        }
        try {
            Customer customer = new Customer();
            customer.setCustomerName(customerDto.getName());
            customer.setCreatedDt(new Date());
            customerRepository.save(customer);
            return true;
        } catch (Exception e) {
            log.error("Error when saving Customer info: {}", e);
            throw new ApplicationException("Error when creating Customer info");
        }
    }

    /**
     * Save Transaction Information
     * @param transactionDto
     */
    @Transactional
    public boolean saveTransaction(TransactionDto transactionDto) {
      Optional<Customer> optional = customerRepository.findById(transactionDto.getCustomerId());
      if(Optional.empty().isPresent()) {
        throw new CustomerNotFoundException("Customer Not found");
      }
      try {
          Transaction transaction = new Transaction();
          transaction.setAmount(transactionDto.getAmount());
          transaction.setCreatedDt(new Date());
          transaction.setCustomer(optional.get());
          Reward reward = new Reward();
          reward.setRewards(getRewardPoints(transactionDto.getAmount()));
          reward.setCreatedDt(new Date());
          transaction.setReward(reward);
          transactionRepository.save(transaction);
          return true;
      } catch (Exception e) {
          log.error("Error when saving Transaction info: {}", e);
        throw new ApplicationException("Error when saving Transaction info");
      }
    }

    /**
     * Get Total Rewards earned in last 3 months for each customer
     * @return List of RewardsInfo
     */
    public List<RewardsInfo> getCustomersMonthlyTotalRewardsOfLastThreeMonths() {
        try {
            List<RewardsInfo> rewardsList = rewardsRepository.findCustomersMonthlyTotalRewardsOfLastThreeMonths();
            return rewardsList;
        } catch (Exception e) {
            log.error("Error in getting rewards info for all customers: {}", e);
            throw new ApplicationException("Error in getting Total rewards Info");
        }
    }

    /**
     * Get Total Rewards earned in last 3 months for a given Customer
     * @param customerId
     * @return RewardsInfo
     */
    public RewardsInfo getLastThreeMonthsTotalRewardsByCustomer(Long customerId) {
        try {
            RewardsInfo rewardsInfo = rewardsRepository.findLastThreeMonthsTotalRewardsByCustomer(customerId);
            return rewardsInfo;
        } catch (Exception e) {
            log.error("Error in getting rewards info for a customer: {}", e);
            throw new ApplicationException("Error in getting Total rewards Info");
        }
    }

    private BigDecimal getRewardPoints(BigDecimal amount) {
        BigDecimal rewardpoints = BigDecimal.ZERO;

        if (amount != null) {
            if (amount.compareTo(new BigDecimal(50)) > 0 && amount.compareTo(new BigDecimal(100)) <= 0) {
                rewardpoints = amount.subtract(new BigDecimal(50));
            }
            if (amount.compareTo(new BigDecimal(100)) > 0) {
                rewardpoints = amount.subtract(new BigDecimal(100)).multiply(new BigDecimal(2));
            }
            if (amount.compareTo(new BigDecimal(100)) > 0 && amount.compareTo(new BigDecimal(50)) >= 0) {
                rewardpoints = rewardpoints.add(new BigDecimal(50));
            }
        }
        return rewardpoints;
    }


}
