package az.spring.bankaccountcqrseventsourcing.command.command;

import java.math.BigDecimal;

public class DepositMoneyCommand extends BaseCommand<String>{

    private final BigDecimal amount;

    public DepositMoneyCommand(String id, BigDecimal amount){
        super(id);
        this.amount=amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}