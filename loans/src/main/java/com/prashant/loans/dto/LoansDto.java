package com.prashant.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(name = "Loans",
    description = "Schema to hold Loan information")
@Data
public class LoansDto {

    @NotEmpty(message = "Mobile Number can not be null or empty")
    @Pattern(regexp = "[0-9]{10}", message = "Invalid mobile number")
    @Schema(description = "Mobile Number", example = "1234567890")
    private String mobileNumber;

    @NotEmpty(message = "Loan Number can not be null or empty")
    @Pattern(regexp = "[0-9]{10}", message = "LoanNumber must be 12 digits")
    @Schema(
            description = "Loan Number of the customer", example = "123412341234"
    )
    private String loanNumber;

    @NotEmpty(message = "Loan Type can not be null or empty")
    @Schema(description = "Loan Type", example = "Home Loan")
    private String loanType;

    @Positive(message = "Total Loan amount should be greater than zero")
    @Schema(description = "Total Loan amount", example = "100000")
    private int totalLoan;

    @PositiveOrZero(message = "Amount paid should be greater than or equal to zero")
    @Schema(description = "Amount paid", example = "50000")
    private int amountPaid;

    @PositiveOrZero(message = "Outstanding amount should be greater than or equal to zero")
    @Schema(description = "Outstanding amount", example = "50000")
    private int outstandingAmount;

}
