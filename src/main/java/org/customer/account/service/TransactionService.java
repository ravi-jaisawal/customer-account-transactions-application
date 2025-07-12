package org.customer.account.service;

import jakarta.transaction.Transactional;
import org.customer.account.dto.TransactionRequestDTO;
import org.customer.account.dto.TransactionResponseDTO;
import org.customer.account.entity.Account;
import org.customer.account.entity.OperationType;
import org.customer.account.entity.Transaction;
import org.customer.account.repository.AccountRepository;
import org.customer.account.repository.OperationTypeRepository;
import org.customer.account.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private OperationTypeRepository opTypeRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO request) {
        // 1. Validate account
        Account account = accountRepo.findByIdForUpdate(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid account ID"));

        // 2. Validate operation type
        OperationType operationType = opTypeRepo.findById(request.getOperationTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation type ID"));

        double amount = request.getAmount();

        // 3. Fetch account balance
        Double balance = transactionRepo.getAccountBalance(account.getAccountId());
        if (balance == null) balance = 0.0;

        // 4. Prevent overdrafts (except for PAYMENT type)
        if (operationType.getOperationTypeId() != 4 && (balance + amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds for this transaction.");
        }

        // 5. Handle INSTALLMENT PURCHASE (ID 2)
        if (operationType.getOperationTypeId() == 2) {
            int installments = 3; // or make dynamic
            double installmentAmount = -Math.abs(amount) / installments;

            for (int i = 0; i < installments; i++) {
                Transaction tx = new Transaction();
                tx.setAccount(account);
                tx.setOperationType(operationType);
                tx.setAmount(installmentAmount);
                tx.setEventDate(LocalDateTime.now().plusMonths(i));
                transactionRepo.save(tx);
            }

            return new TransactionResponseDTO(
                    null,
                    account.getAccountId(),
                    operationType.getOperationTypeId(),
                    amount
            );
        }

        // 6. Normalize negative amount for debt operations
        if (operationType.getOperationTypeId() != 4 && amount > 0) {
            amount = -amount;
        }

        // 7. Save standard transaction
        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setOperationType(operationType);
        tx.setAmount(amount);

        Transaction saved = transactionRepo.save(tx);

        return new TransactionResponseDTO(
                saved.getTransactionId(),
                saved.getAccount().getAccountId(),
                saved.getOperationType().getOperationTypeId(),
                saved.getAmount()
        );
    }


    public Optional<TransactionResponseDTO> getTransactionById(Long id) {
        return transactionRepo.findById(id).map(tx ->
                new TransactionResponseDTO(
                        tx.getTransactionId(),
                        tx.getAccount().getAccountId(),
                        tx.getOperationType().getOperationTypeId(),
                        tx.getAmount()
                )
        );
    }
}