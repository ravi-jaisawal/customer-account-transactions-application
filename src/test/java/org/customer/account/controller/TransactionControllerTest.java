package org.customer.account.controller;

import org.customer.account.dto.TransactionRequestDTO;
import org.customer.account.dto.TransactionResponseDTO;
import org.customer.account.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testPostTransaction_shouldReturnOk() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setAccountId(1L);
        request.setOperationTypeId(1L);
        request.setAmount(50.0);

        TransactionResponseDTO response = new TransactionResponseDTO(1L, 1L, 1L, -50.0);

        Mockito.when(transactionService.createTransaction(Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "account_id": 1,
                          "operation_type_id": 1,
                          "amount": 50.0
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id").value(1L))
                .andExpect(jsonPath("$.amount").value(-50.0));
    }
}