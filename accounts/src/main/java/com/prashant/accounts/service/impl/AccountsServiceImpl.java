package com.prashant.accounts.service.impl;

import com.prashant.accounts.constants.AccountsConstants;
import com.prashant.accounts.dto.AccountsDto;
import com.prashant.accounts.dto.CustomerDto;
import com.prashant.accounts.entity.Accounts;
import com.prashant.accounts.entity.Customer;
import com.prashant.accounts.exception.CustomerAlreadyExistsException;
import com.prashant.accounts.exception.ResourceNotFoundException;
import com.prashant.accounts.mapper.AccountsMapper;
import com.prashant.accounts.mapper.CustomerMapper;
import com.prashant.accounts.repository.AccountsRepository;
import com.prashant.accounts.repository.CustomerRepository;
import com.prashant.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber"+customer.getMobileNumber());
        }
        customerRepository.save(customer);
        accountsRepository.save(createNewAccount(customer));

    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 100000000L + new Random().nextLong(90000000L);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
               ()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
       );
       Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
               ()->new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString())
       );

       CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
       customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto!=null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()->new ResourceNotFoundException("Account","AccountNumber",accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    ()->new ResourceNotFoundException("Customer","CustomerID",customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
