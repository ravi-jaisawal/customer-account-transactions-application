package org.customer.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.customer.account.dto.TransactionRequestDTO;
import org.customer.account.dto.TransactionResponseDTO;
import org.customer.account.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transaction APIs", description = "Operations related to financial transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create a transaction", description = "Creates a transaction for a given account and operation type")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequestDTO request) {
        try {
            TransactionResponseDTO response = transactionService.createTransaction(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID", description = "Retrieves transaction details by ID")
    public ResponseEntity<?> getTransactionById(@PathVariable("id") Long id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
