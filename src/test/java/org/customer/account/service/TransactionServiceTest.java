package org.customer.account.service;

import org.customer.account.dto.TransactionRequestDTO;
import org.customer.account.dto.TransactionResponseDTO;
import org.customer.account.entity.Account;
import org.customer.account.entity.OperationType;
import org.customer.account.entity.Transaction;
import org.customer.account.repository.AccountRepository;
import org.customer.account.repository.OperationTypeRepository;
import org.customer.account.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionServiceTest {

    @MockBean
    private AccountRepository accountRepo;

    @MockBean
    private OperationTypeRepository opTypeRepo;

    @MockBean
    private TransactionRepository transactionRepo;

    @Autowired
    private TransactionService transactionService;

    @Test
    void testCreateTransaction_validPurchase_shouldSave() {
        Long accountId = 1L;
        Long operationTypeId = 1L;
        double amount = 50.0;

        Account account = new Account();
        account.setAccountId(accountId);

        OperationType opType = new OperationType();
        opType.setOperationTypeId(operationTypeId);
        opType.setDescription("PURCHASE");

        Mockito.when(accountRepo.findById(accountId)).thenReturn(Optional.of(account));
        Mockito.when(opTypeRepo.findById(operationTypeId)).thenReturn(Optional.of(opType));
        Mockito.when(transactionRepo.getAccountBalance(accountId)).thenReturn(100.0);
        Mockito.when(transactionRepo.save(Mockito.any())).thenAnswer(i -> {
            Transaction t = i.getArgument(0);
            t.setTransactionId(1L);
            return t;
        });

        TransactionRequestDTO req = new TransactionRequestDTO();
        req.setAccountId(accountId);
        req.setOperationTypeId(operationTypeId);
        req.setAmount(amount);

        TransactionResponseDTO result = transactionService.createTransaction(req);

        Assertions.assertEquals(-50.0, result.getAmount());
        Assertions.assertEquals(accountId, result.getAccountId());
    }
}