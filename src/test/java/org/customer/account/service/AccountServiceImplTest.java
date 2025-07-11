package org.customer.account.service;

import org.customer.account.dto.AccountDTO;
import org.customer.account.entity.Account;
import org.customer.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    void testCreateAccount_success() {
        String documentNumber = "12345678900";
        Account account = new Account(1L, documentNumber);

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDTO result = accountService.createAccount(documentNumber);

        assertNotNull(result);
        assertEquals(1L, result.getAccountId());
        assertEquals(documentNumber, result.getDocumentNumber());
    }

    @Test
    void testGetAccountById_success() {
        Long accountId = 1L;
        String documentNumber = "12345678900";
        Account account = new Account(accountId, documentNumber);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        AccountDTO result = accountService.getAccountById(accountId);

        assertNotNull(result);
        assertEquals(accountId, result.getAccountId());
        assertEquals(documentNumber, result.getDocumentNumber());
    }

    @Test
    void testGetAccountById_notFound_shouldThrowException() {
        Long accountId = 999L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                accountService.getAccountById(accountId));

        assertEquals("Account not found for id: 999", exception.getMessage());
    }
}
