package org.customer.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.customer.account.dto.AccountDTO;
import org.customer.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account APIs", description = "Operations related to customer accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    @Operation(summary = "Create a new account", description = "Creates a new account with the given document number")
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO request) {
        try {
            AccountDTO created = accountService.createAccount(request.getDocumentNumber());
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Get account by ID", description = "Retrieves account details by account ID")
    public ResponseEntity<?> getAccount(@PathVariable Long accountId) {
        try {
            AccountDTO account = accountService.getAccountById(accountId);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
