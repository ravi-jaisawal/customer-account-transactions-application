package org.customer.account.repository;

import org.customer.account.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.account.accountId = :accountId")
    Double getAccountBalance(@Param("accountId") Long accountId);
}