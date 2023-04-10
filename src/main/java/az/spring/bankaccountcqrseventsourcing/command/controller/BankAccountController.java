package az.spring.bankaccountcqrseventsourcing.command.controller;

import az.spring.bankaccountcqrseventsourcing.command.dto.CreateAccountRequest;
import az.spring.bankaccountcqrseventsourcing.command.dto.DepositRequest;
import az.spring.bankaccountcqrseventsourcing.command.dto.WithdrawalRequest;
import az.spring.bankaccountcqrseventsourcing.command.service.AccountCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/bank-account")
@RequiredArgsConstructor
public class BankAccountController {

    private final AccountCommandService accountCommandService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest createRequest) {
        try {
            CompletableFuture<String> response = accountCommandService.createAccount(createRequest);
            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositRequest depositRequest) {
        try {
            accountCommandService.depositToAccount(depositRequest);
            return new ResponseEntity<>("Amount credited.", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawalRequest withdrawalRequest) {
        try {
            accountCommandService.withdrawFromAccount(withdrawalRequest);
            return new ResponseEntity<>("Amount debited.", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}