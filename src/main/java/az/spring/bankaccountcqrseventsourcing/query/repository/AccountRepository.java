package az.spring.bankaccountcqrseventsourcing.query.repository;

import az.spring.bankaccountcqrseventsourcing.query.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {


}