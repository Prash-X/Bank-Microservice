package com.prashant.loans.dto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@Data @AllArgsConstructor
public class ResponseDto {

    @Schema(description = "Status Code in the response")
    private String statusCode;

    @Schema(description = "Status Message in the response")
    private String statusMsg;
}
