package org.customer.account.service;

import org.customer.account.dto.AccountDTO;

public interface AccountService {
    AccountDTO createAccount(String documentNumber);
    AccountDTO getAccountById(Long accountId);
}
