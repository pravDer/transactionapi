package com.pravder.money.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class TransferRequestTest {

  private final ObjectMapper mapper = new ObjectMapper();
  private static final String TRANSFER_REQUEST_JSON = "transferRequest.json";

  @Test
  public void testTransferRequestDeserialization() throws IOException {
    TransferRequest transferRequest = mapper.readValue(
        this.getClass().getClassLoader().getResource(TRANSFER_REQUEST_JSON),
        TransferRequest.class);

    Assertions.assertNotNull(transferRequest);
    Assertions.assertEquals("acct_456", transferRequest.getFromAccountNumber());
    Assertions.assertEquals("acct_123", transferRequest.getToAccountNumber());
    Assertions.assertEquals(BigDecimal.valueOf(1000.0), transferRequest.getMoney().getBalance());
    Assertions.assertEquals("USD", transferRequest.getMoney().getCurrency());
  }
}
