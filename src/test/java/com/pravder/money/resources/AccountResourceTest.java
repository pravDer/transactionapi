package com.pravder.money.resources;

import com.gs.fw.common.mithra.test.ConnectionManagerForTests;
import com.gs.fw.common.mithra.test.MithraTestResource;
import com.pravder.money.api.BalanceResponse;
import com.pravder.money.api.TransactionResponse;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class AccountResourceTest {

  private static AccountResource accountResource = new AccountResource();
  private static MithraTestResource mithraTestResource;

  @BeforeAll
  public static void setUp() throws Exception {
    mithraTestResource = new MithraTestResource("ReladomoTestConfig.xml");
    ConnectionManagerForTests connectionManager = ConnectionManagerForTests.getInstanceForDbName("testDb");
    mithraTestResource.createSingleDatabase(connectionManager);
    mithraTestResource.addTestDataToDatabase("test-data.txt", connectionManager);
    mithraTestResource.setUp();
  }

  @AfterAll
  public static void tearDown() {
    mithraTestResource.tearDown();
  }

  @Test
  public void testGetAccountBalance() {
    List<BalanceResponse> balanceRespons = accountResource.getAccountBalance("acct_1");
    Assertions.assertNotNull(balanceRespons);
    Assertions.assertEquals(2, balanceRespons.size());
    for (BalanceResponse balanceResponse : balanceRespons) {
      if (balanceResponse.getMoney().getCurrency().equals("USD")) {
        Assertions.assertTrue(
            balanceResponse.getMoney().getBalance().subtract(BigDecimal.valueOf(200)).compareTo(BigDecimal.valueOf(0.005)) < 0);
      }
    }
  }

  @Test
  public void testGetTransactionsForAccount() {
    List<TransactionResponse> transactionResponses = accountResource.getTransactionsForAccount("acct_1");
    Assertions.assertNotNull(transactionResponses);
  }

}
