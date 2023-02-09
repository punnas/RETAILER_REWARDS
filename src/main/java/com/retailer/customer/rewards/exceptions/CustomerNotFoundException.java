package com.retailer.customer.rewards.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerNotFoundException extends RuntimeException{
    private String errorMsg;

}
