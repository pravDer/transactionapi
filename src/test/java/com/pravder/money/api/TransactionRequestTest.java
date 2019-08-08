package com.pravder.money.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TransactionRequestTest {

  private final ObjectMapper mapper = new ObjectMapper();
  private static final String TRANSACTION_REQUEST_JSON = "transactionRequest.json";

  @Test
  public void testTransactionRequestDeserialization() throws IOException {
    TransactionRequest transactionRequest = mapper.readValue(
        this.getClass().getClassLoader().getResource(TRANSACTION_REQUEST_JSON),
        TransactionRequest.class);

    Assertions.assertNotNull(transactionRequest);
    Assertions.assertEquals("acct_123", transactionRequest.getAccountNumber());
    Assertions.assertEquals(BigDecimal.valueOf(1000.0), transactionRequest.getMoney().getBalance());
    Assertions.assertEquals("USD", transactionRequest.getMoney().getCurrency());
  }
}
