package az.spring.bankaccountcqrseventsourcing.command.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawalRequest {

    private String accountId;
    private BigDecimal amount;

}