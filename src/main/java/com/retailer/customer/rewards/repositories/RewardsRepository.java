package com.retailer.customer.rewards.repositories;

import com.retailer.customer.rewards.entities.RewardsInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardsRepository extends CrudRepository <RewardsInfo, Long> {

    @Query(nativeQuery = true, value = "SELECT c.CUSTOMER_ID as CUSTOMER_ID, c.CUSTOMER_NAME AS CUSTOMER_NAME, MONTH(CURRENT_DATE()) AS TRANSACTION_MONTH, " +
            "YEAR(CURRENT_DATE()) AS TRANSACTION_YEAR, SUM(r.REWARDS) AS TOTAL_REWARDS FROM REWARD as r " +
            "JOIN TRANSACTION AS t ON t.REWARD_ID = r.REWARD_ID JOIN CUSTOMER AS c ON c.CUSTOMER_ID  = t.CUSTOMER_ID  " +
            "WHERE (t.CREATED_DT <= CURRENT_DATE() AND t.CREATED_DT  >= DATEADD('MONTH',-3, CURRENT_DATE())) " +
            "GROUP BY (c.CUSTOMER_NAME, MONTH(CURRENT_DATE()), YEAR(CURRENT_DATE()))")
    List<RewardsInfo> findCustomersMonthlyTotalRewardsOfLastThreeMonths();

    @Query(nativeQuery = true, value = "SELECT c.CUSTOMER_ID as CUSTOMER_ID, c.CUSTOMER_NAME AS CUSTOMER_NAME, MONTH(CURRENT_DATE()) AS TRANSACTION_MONTH, " +
            "YEAR(CURRENT_DATE()) AS TRANSACTION_YEAR, SUM(r.REWARDS) AS TOTAL_REWARDS FROM REWARD as r " +
            "JOIN TRANSACTION AS t ON t.REWARD_ID = r.REWARD_ID JOIN CUSTOMER AS c ON c.CUSTOMER_ID  = t.CUSTOMER_ID  " +
            "WHERE (t.CREATED_DT <= CURRENT_DATE() AND t.CREATED_DT  >= DATEADD('MONTH',-3, CURRENT_DATE())) AND c.CUSTOMER_ID = :customerId " +
            "GROUP BY (c.CUSTOMER_NAME, MONTH(CURRENT_DATE()), YEAR(CURRENT_DATE()))\n")
    RewardsInfo findLastThreeMonthsTotalRewardsByCustomer(@Param("customerId") Long customerId);
}
