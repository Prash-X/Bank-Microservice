package com.prashant.accounts.service;

import com.prashant.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId);
}
