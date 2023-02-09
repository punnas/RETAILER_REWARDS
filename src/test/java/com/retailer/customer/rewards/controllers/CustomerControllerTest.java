package com.retailer.customer.rewards.controllers;

import com.retailer.customer.rewards.entities.RewardsInfo;
import com.retailer.customer.rewards.exceptions.ApplicationException;
import com.retailer.customer.rewards.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test Customer Rewards API's
 */
@WebMvcTest(value = CustomerController.class )
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private final String customerPayLoad = "{\"name\":\"Test Name\"}";
    @BeforeEach
    void setUp() {

    }

    /**
     * Test Save Customer Info
     * @throws Exception
     */
    @Test
    void testSaveCustomerCreated() throws Exception {
        Mockito.when(customerService.saveCustomer(Mockito.any())).thenReturn(true);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/customer").content(customerPayLoad)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    /**
     * Test Successful when retrieving Rewards Info
     * @throws Exception
     */
    @Test
    void testGetRewardsInfoSuccess() throws Exception {
        List<RewardsInfo> rewardsInfoList = getRewardsInfoList();
        Mockito.when(customerService.getCustomersMonthlyTotalRewardsOfLastThreeMonths()).thenReturn(rewardsInfoList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/rewards").content(customerPayLoad)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test 204- No Content
     * @throws Exception
     */
    @Test
    void testGetRewardsInfoNoContentFound() throws Exception {
         Mockito.when(customerService.getCustomersMonthlyTotalRewardsOfLastThreeMonths()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/rewards").content(customerPayLoad)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Test 500 - Internal Server Error
     * @throws Exception
     */
    @Test
    void testGetRewardsInfoInternalServerError() throws Exception {
        Mockito.when(customerService.getCustomersMonthlyTotalRewardsOfLastThreeMonths()).thenThrow(new ApplicationException("Error when retrieving Rewards Info"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/rewards").content(customerPayLoad)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test Get Reward info a Customer
     * @throws Exception
     */
    @Test
    void testGetRewardsInfoByCustomerSuccess() throws Exception {

        Mockito.when(customerService.getLastThreeMonthsTotalRewardsByCustomer(Mockito.anyLong())).thenReturn(getRewardInfo());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/rewards/1").content(customerPayLoad)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test Get Reward info of a Customer - return empty
     * @throws Exception
     */
    @Test
    void testGetRewardsInfoByCustomerNoContent() throws Exception {

        Mockito.when(customerService.getLastThreeMonthsTotalRewardsByCustomer(Mockito.anyLong())).thenReturn(null);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/rewards/1").content(customerPayLoad)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Test Get Reward info of a Customer - Internal Server Error
     * @throws Exception
     */
    @Test
    void testGetRewardsInfoByCustomerInternalServerError() throws Exception {

        Mockito.when(customerService.getLastThreeMonthsTotalRewardsByCustomer(Mockito.anyLong()))
                .thenThrow(new ApplicationException("Error when retrieving rewards info fur customer"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/rewards/1").content(customerPayLoad)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    private List<RewardsInfo> getRewardsInfoList() {
        List<RewardsInfo> rewardsInfoList = new ArrayList<>();
        rewardsInfoList.add(getRewardInfo());
        return rewardsInfoList;
    }

    private RewardsInfo getRewardInfo() {
        RewardsInfo rewardsInfo = new RewardsInfo();
        rewardsInfo.setCustomerId(1);
        rewardsInfo.setTotalRewards(200);
        rewardsInfo.setCustomerName("Test Name");
        rewardsInfo.setTransactionMonth(2);
        rewardsInfo.setTransactionYear(2023);
        return rewardsInfo;
    }

}