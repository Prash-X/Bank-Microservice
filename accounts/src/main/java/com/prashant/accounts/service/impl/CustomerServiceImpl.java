package com.prashant.accounts.service.impl;

import com.prashant.accounts.dto.AccountsDto;
import com.prashant.accounts.dto.CardsDto;
import com.prashant.accounts.dto.CustomerDetailsDto;
import com.prashant.accounts.dto.LoansDto;
import com.prashant.accounts.entity.Accounts;
import com.prashant.accounts.entity.Customer;
import com.prashant.accounts.exception.ResourceNotFoundException;
import com.prashant.accounts.mapper.AccountsMapper;
import com.prashant.accounts.mapper.CustomerMapper;
import com.prashant.accounts.repository.AccountsRepository;
import com.prashant.accounts.repository.CustomerRepository;
import com.prashant.accounts.service.ICustomerService;
import com.prashant.accounts.service.client.CardsFeignClient;
import com.prashant.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(()-> new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
