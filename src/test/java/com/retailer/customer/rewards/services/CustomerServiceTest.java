package com.retailer.customer.rewards.services;

import com.retailer.customer.rewards.dtos.CustomerDto;
import com.retailer.customer.rewards.dtos.TransactionDto;
import com.retailer.customer.rewards.entities.Customer;
import com.retailer.customer.rewards.entities.Reward;
import com.retailer.customer.rewards.entities.RewardsInfo;
import com.retailer.customer.rewards.entities.Transaction;
import com.retailer.customer.rewards.exceptions.ApplicationException;
import com.retailer.customer.rewards.repositories.CustomerRepository;
import com.retailer.customer.rewards.repositories.RewardsRepository;
import com.retailer.customer.rewards.repositories.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RewardsRepository rewardsRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void saveCustomer() {
        Customer customer = new Customer();
        customer.setCustomerName("Test Name");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Test Name");
        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);
        boolean returnVal = customerService.saveCustomer(customerDto);
        Assert.isTrue(returnVal);
    }


    @Test(expected = ApplicationException.class)
    public void saveCustomerNameNullCheck() throws ApplicationException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(null);
        customerService.saveCustomer(customerDto);
    }

    @Test(expected = ApplicationException.class)
    public void saveCustomerNameThrowException() throws ApplicationException {

        Customer customer = new Customer();
        customer.setCustomerName("Test Name");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Test Name");
        Mockito.when(customerRepository.save(Mockito.any())).thenThrow(ApplicationException.class);
        customerService.saveCustomer(customerDto);
    }

    @Test
    public void testSaveTransaction() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setCustomerId(1L);
        transactionDto.setAmount(new BigDecimal(60));;

        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);

        Reward reward = new Reward();
        reward.setRewards(new BigDecimal(10));
        reward.setRewardId(1L);
        transaction.setReward(reward);
        Customer customer = new Customer();
        customer.setCustomerName("Test Name");
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transaction);
        boolean returnVal = customerService.saveTransaction(transactionDto);
        Assert.isTrue(returnVal);
    }

    @Test (expected = ApplicationException.class)
    public void testSaveTransactionException() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setCustomerId(1L);
        transactionDto.setAmount(new BigDecimal(60));;

        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);

        Reward reward = new Reward();
        reward.setRewards(new BigDecimal(10));
        reward.setRewardId(1L);
        transaction.setReward(reward);
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Customer()));
        Mockito.when(transactionRepository.save(Mockito.any())).thenThrow(ApplicationException.class);
        customerService.saveTransaction(transactionDto);
    }

    @Test
    public void testGetCustomersMonthlyTotalRewardsOfLastThreeMonths() {
        Mockito.when(rewardsRepository.findCustomersMonthlyTotalRewardsOfLastThreeMonths()).thenReturn(getRewardsInfoList());
        List<RewardsInfo> rewardsInfoList =  customerService.getCustomersMonthlyTotalRewardsOfLastThreeMonths();
        Assert.notNull(rewardsInfoList);
        Assert.notEmpty(rewardsInfoList);
        Assert.isTrue(rewardsInfoList.get(0).getCustomerName().equals("Test Name"));
    }

    @Test (expected = ApplicationException.class)
    public void testGetCustomersMonthlyTotalRewardsOfLastThreeMonthsException() {
        Mockito.when(rewardsRepository.findCustomersMonthlyTotalRewardsOfLastThreeMonths()).thenThrow(ApplicationException.class);
        customerService.getCustomersMonthlyTotalRewardsOfLastThreeMonths();
    }

    @Test
    public void testGetLastThreeMonthsTotalRewardsByCustomer() {
        Mockito.when(rewardsRepository.findLastThreeMonthsTotalRewardsByCustomer(1L)).thenReturn(getRewardInfo());
        RewardsInfo rewardsInfo =  customerService.getLastThreeMonthsTotalRewardsByCustomer(1L);
        Assert.notNull(rewardsInfo);
        Assert.isTrue(rewardsInfo.getCustomerName().equals("Test Name"));
    }

    @Test(expected = ApplicationException.class)
    public void testGetLastThreeMonthsTotalRewardsByCustomerException() {
        Mockito.when(rewardsRepository.findLastThreeMonthsTotalRewardsByCustomer(1L)).thenThrow(ApplicationException.class);
        customerService.getLastThreeMonthsTotalRewardsByCustomer(1L);
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