package com.retailer.customer.rewards.controllers;

import com.retailer.customer.rewards.exceptions.ApplicationException;
import com.retailer.customer.rewards.exceptions.TransactionNotFoundException;
import com.retailer.customer.rewards.exceptions.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RewardsExceptionController {

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<Object> exception(CustomerNotFoundException customerNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customerNotFoundException);
    }

    @ExceptionHandler(value = TransactionNotFoundException.class)
    public ResponseEntity<Object> exception1(TransactionNotFoundException transactionNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionNotFoundException);
    }

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<Object> exception1(ApplicationException applicationException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(applicationException);
    }


}
