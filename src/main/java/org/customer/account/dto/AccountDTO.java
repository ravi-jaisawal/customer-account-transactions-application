package org.customer.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountDTO {

    private Long accountId;
    @JsonProperty("document_number")
    private String documentNumber;

    public AccountDTO() {}

    public AccountDTO(Long accountId, String documentNumber) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
    }

    // Getters and Setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
