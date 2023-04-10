package az.spring.bankaccountcqrseventsourcing.common.event;

import java.math.BigDecimal;

public class AccountDebitedEvent extends BaseEvent<String> {

    private final BigDecimal amount;

    public AccountDebitedEvent(String id, BigDecimal amount) {
        super(id);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}