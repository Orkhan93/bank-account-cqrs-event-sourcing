package az.spring.bankaccountcqrseventsourcing.command.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {

    private BigDecimal startingBalance;

}