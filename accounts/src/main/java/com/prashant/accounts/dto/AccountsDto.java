package com.prashant.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts",
        description = "Schema to hold Account information")
@Data
public class AccountsDto {

    @NotEmpty(message = "Account number should not be empty")
    @Pattern(regexp = "([0-9]{10})", message = "Mobile number should be 10 digits")
    @Schema(description = "Account number")
    private Long accountNumber;
    @NotEmpty(message = "Account type should not be empty")
    private String accountType;
    @NotEmpty(message = "Branch address should not be empty")
    private String branchAddress;

}
