package org.customer.account.service;

import org.customer.account.dto.AccountDTO;
import org.customer.account.entity.Account;
import org.customer.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository repository;

    public AccountServiceImpl(AccountRepository accountRepo) {
        this.repository = accountRepo;
    }

    @Override
    public AccountDTO createAccount(String documentNumber) {
        Account account = new Account();
        account.setDocumentNumber(documentNumber);
        Account saved = repository.save(account);
        return new AccountDTO(saved.getAccountId(), saved.getDocumentNumber());
    }

    @Override
    public AccountDTO getAccountById(Long accountId) {
        Account account = repository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for id: " + accountId));
        return new AccountDTO(account.getAccountId(), account.getDocumentNumber());
    }
}
