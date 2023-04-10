package az.spring.bankaccountcqrseventsourcing.command.service;

import az.spring.bankaccountcqrseventsourcing.command.command.CreateAccountCommand;
import az.spring.bankaccountcqrseventsourcing.command.command.DepositMoneyCommand;
import az.spring.bankaccountcqrseventsourcing.command.command.WithdrawMoneyCommand;
import az.spring.bankaccountcqrseventsourcing.command.dto.CreateAccountRequest;
import az.spring.bankaccountcqrseventsourcing.command.dto.DepositRequest;
import az.spring.bankaccountcqrseventsourcing.command.dto.WithdrawalRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AccountCommandService {

    private final CommandGateway commandGateway;

    public CompletableFuture<String> createAccount(CreateAccountRequest createAccountRequest) {
        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                createAccountRequest.getStartingBalance()
        ));
    }

    public CompletableFuture<String> depositToAccount(DepositRequest depositRequest) {
        return commandGateway.send(new DepositMoneyCommand(
                depositRequest.getAccountId(),
                depositRequest.getAmount()
        ));
    }

    public CompletableFuture<String> withdrawFromAccount(WithdrawalRequest withdrawalRequest) {
        return commandGateway.send(new WithdrawMoneyCommand(
                withdrawalRequest.getAccountId(),
                withdrawalRequest.getAmount()
        ));
    }

}