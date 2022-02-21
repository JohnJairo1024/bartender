package com.co.bar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BarDto {

    private int responseCode;
    private String message;
    private String transactionId;
    private String data;

}
