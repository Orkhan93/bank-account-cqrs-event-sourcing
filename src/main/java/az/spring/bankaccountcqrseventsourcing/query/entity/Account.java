package az.spring.bankaccountcqrseventsourcing.query.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Account {

    @Id
    private String accountId;
    private BigDecimal balance;
    private String status;

}