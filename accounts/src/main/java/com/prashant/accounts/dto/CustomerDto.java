package com.prashant.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
    name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer", example = "Prashant"
    )
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5,max = 30, message = "Name should have at least 5 characters")
    private String name;

    @Schema(
            description = "Email of the customer", example = "Prashant@Gmail.com"
    )
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            description = "Name of the customer", example = "Prashant"
    )
    @Pattern(regexp = "([0-9]{10})", message = "Mobile number should be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
