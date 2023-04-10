package az.spring.bankaccountcqrseventsourcing.query.service;

import az.spring.bankaccountcqrseventsourcing.common.event.AccountActivatedEvent;
import az.spring.bankaccountcqrseventsourcing.common.event.AccountCreatedEvent;
import az.spring.bankaccountcqrseventsourcing.common.event.AccountCreditedEvent;
import az.spring.bankaccountcqrseventsourcing.common.event.AccountDebitedEvent;
import az.spring.bankaccountcqrseventsourcing.query.entity.Account;
import az.spring.bankaccountcqrseventsourcing.query.query.FindAccountByIdQuery;
import az.spring.bankaccountcqrseventsourcing.query.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManageAccountService {

    private final AccountRepository accountRepository;

    @EventHandler
    public void on(AccountCreatedEvent createdEvent) {
        log.info("Handling AccountCreatedEvent...");
        Account account = new Account();
        account.setAccountId(createdEvent.getId());
        account.setBalance(createdEvent.getBalance());
        account.setStatus("Created");

        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent activatedEvent) {
        log.info("Handling AccountActivatedEvent...");
        Account account = accountRepository.findById(activatedEvent.getId())
                .orElse(null);
        if (account != null) {
            account.setStatus(activatedEvent.getStatus());
            accountRepository.save(account);
        }
    }

    @EventHandler
    public void on(AccountCreditedEvent creditedEvent) {
        log.info("Handling AccountCreditedEvent...");
        Account account = accountRepository.findById(creditedEvent.getId())
                .orElse(null);
        if (account != null) {
            account.setBalance(account.getBalance().add(creditedEvent.getAmount()));
            accountRepository.save(account);
        }
    }

    @EventHandler
    public void on(AccountDebitedEvent debitedEvent) {
        log.info("Handling AccountDebitedEvent...");
        Account account = accountRepository.findById(debitedEvent.getId())
                .orElse(null);
        if (account != null) {
            account.setBalance(account.getBalance().add(debitedEvent.getAmount()));
            accountRepository.save(account);
        }

    }

    @QueryHandler
    public Account handle(FindAccountByIdQuery query) {
        log.info("Handling FindAccountByIdQuery...");
        Account account = accountRepository.findById(query.getAccountId())
                .orElse(null);
        return account;
    }

}