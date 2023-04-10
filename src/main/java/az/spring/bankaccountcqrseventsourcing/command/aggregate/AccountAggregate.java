package az.spring.bankaccountcqrseventsourcing.command.aggregate;

import az.spring.bankaccountcqrseventsourcing.command.command.CreateAccountCommand;
import az.spring.bankaccountcqrseventsourcing.command.command.DepositMoneyCommand;
import az.spring.bankaccountcqrseventsourcing.command.command.WithdrawMoneyCommand;
import az.spring.bankaccountcqrseventsourcing.common.event.AccountActivatedEvent;
import az.spring.bankaccountcqrseventsourcing.common.event.AccountCreatedEvent;
import az.spring.bankaccountcqrseventsourcing.common.event.AccountCreditedEvent;
import az.spring.bankaccountcqrseventsourcing.common.event.AccountDebitedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
@Slf4j
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private BigDecimal balance;
    private String status;

    public AccountAggregate(){

    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        log.info("CreateAccountCommand received.");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getBalance()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        log.info("An AccountCreatedEvent occurred.");
        this.accountId = accountCreatedEvent.getId();
        this.balance = accountCreatedEvent.getBalance();
        this.status = "CREATED";

        AggregateLifecycle.apply(new AccountActivatedEvent(
                this.accountId,
                "ACTIVATED"
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent) {
        log.info("An AccountActivatedEvent occurred.");
        this.status = accountActivatedEvent.getStatus();
    }

    @CommandHandler
    public void on(DepositMoneyCommand depositMoneyCommand) {
        log.info("DepositMoneyCommand received.");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                depositMoneyCommand.getId(),
                depositMoneyCommand.getAmount()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEventEvent) {
        log.info("An AccountCreditedEvent occurred.");
        this.balance = this.balance.add(accountCreditedEventEvent.getAmount());
    }

    @CommandHandler
    public void on(WithdrawMoneyCommand withdrawMoneyCommand) {
        log.info("WithdrawMoneyCommand received.");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                withdrawMoneyCommand.getId(),
                withdrawMoneyCommand.getAmount()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent) {
        log.info("An AccountDebitedEvent occurred.");
        this.balance = this.balance.subtract(accountDebitedEvent.getAmount());
    }

}