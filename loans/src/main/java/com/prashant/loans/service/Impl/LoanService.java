package com.prashant.loans.service.Impl;

import com.prashant.loans.constants.LoansConstants;
import com.prashant.loans.dto.LoansDto;
import com.prashant.loans.entity.Loans;
import com.prashant.loans.exception.LoanAlreadyExistsException;
import com.prashant.loans.exception.ResourceNotFoundException;
import com.prashant.loans.mapper.LoansMapper;
import com.prashant.loans.repository.LoansRepository;
import com.prashant.loans.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoanService implements ILoanService {

    @Autowired
    LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loans = loansRepository.findByMobileNumber(mobileNumber);
        if(loans.isPresent()){
            throw new LoanAlreadyExistsException("Loan with mobile number "+mobileNumber+" already exists");
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 10000000L + new Random().nextInt(90000000);
        newLoan.setLoanNumber(String.valueOf(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;

    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan","mobileNumber",mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        boolean isUpdated = false;
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                ()->new ResourceNotFoundException("Loan","mobileNumber is",loansDto.getMobileNumber())
        );
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan","mobileNumber",mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
