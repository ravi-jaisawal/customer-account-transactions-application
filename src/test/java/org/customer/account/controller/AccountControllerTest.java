package org.customer.account.controller;


import org.customer.account.dto.AccountDTO;
import org.customer.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void testCreateAccount_success() throws Exception {
        AccountDTO mockAccount = new AccountDTO(1L, "12345678900");

        when(accountService.createAccount("12345678900")).thenReturn(mockAccount);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"document_number\": \"12345678900\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(1))
                .andExpect(jsonPath("$.document_number").value("12345678900"));
    }

    @Test
    void testCreateAccount_invalid_shouldReturnBadRequest() throws Exception {
        when(accountService.createAccount("bad-doc")).thenThrow(new IllegalArgumentException("Invalid document"));

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"document_number\": \"bad-doc\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Error: Invalid document")));
    }

    @Test
    void testGetAccountById_success() throws Exception {
        AccountDTO mockAccount = new AccountDTO(1L, "12345678900");

        when(accountService.getAccountById(1L)).thenReturn(mockAccount);

        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(1))
                .andExpect(jsonPath("$.document_number").value("12345678900"));
    }

    @Test
    void testGetAccountById_notFound_shouldReturnBadRequest() throws Exception {
        when(accountService.getAccountById(999L))
                .thenThrow(new IllegalArgumentException("Account not found"));

        mockMvc.perform(get("/accounts/999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Error: Account not found")));
    }
}