package com.retailer.customer.rewards.controllers;

import com.retailer.customer.rewards.services.CustomerService;
import com.retailer.customer.rewards.dtos.CustomerDto;
import com.retailer.customer.rewards.dtos.TransactionDto;
import com.retailer.customer.rewards.entities.RewardsInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Save or update Customer details
 * @author Suresh Punna
 */
@RestController
@RequestMapping(value = "/customer")

public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Create customer info")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Customer is created"),
            @ApiResponse(responseCode = "500", description = "Internal Error")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveCustomer(@RequestBody CustomerDto customerInfo) {
        customerService.saveCustomer(customerInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Create transaction info")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Transaction is created"),
            @ApiResponse(responseCode = "500", description = "Internal Error")})
    @PostMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createTransaction(@RequestBody TransactionDto transactionDto) {
        customerService.saveTransaction(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "This API is to get Total Rewards earned in Last 3 months for each Customer")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rewards Found"),
            @ApiResponse(responseCode = "204", description = "No Content Found"),
            @ApiResponse(responseCode = "500", description = "Error in the code")})
    @GetMapping(value = "/rewards")
    public ResponseEntity getRewardsInfo() {
        List<RewardsInfo> rewardsInfoList = customerService.getCustomersMonthlyTotalRewardsOfLastThreeMonths();
        if(rewardsInfoList == null || rewardsInfoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(rewardsInfoList);
    }

    @Operation(summary = "This API is to get Total Rewards earned in Last 3 months for a given customer")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Rewards Found"),
            @ApiResponse(responseCode = "204", description = "No Content Found"),
            @ApiResponse(responseCode = "500", description = "Error in the code")})
    @GetMapping(value = "/rewards/{customerId}")
    public ResponseEntity getRewardsInfoByCustomer(@PathVariable("customerId") Long customerId) {
        RewardsInfo rewardsInfo = customerService.getLastThreeMonthsTotalRewardsByCustomer(customerId);
        if(rewardsInfo == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(rewardsInfo);
    }

}
