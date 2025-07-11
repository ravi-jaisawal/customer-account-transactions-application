package org.customer.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResponseDTO {

    @JsonProperty("transaction_id")
    private Long transactionId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("operation_type_id")
    private Long operationTypeId;

    private Double amount;

    public TransactionResponseDTO(Long transactionId, Long accountId, Long operationTypeId, Double amount) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
    }

    // Getters
    public Long getTransactionId() {
        return transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public Double getAmount() {
        return amount;
    }

}
