package com.pravder.money.resources;

import com.gs.fw.common.mithra.test.ConnectionManagerForTests;
import com.gs.fw.common.mithra.test.MithraTestResource;
import com.pravder.money.api.Money;
import com.pravder.money.api.TransactionRequest;
import com.pravder.money.api.TransferRequest;
import com.pravder.money.db.mithra.Account;
import com.pravder.money.db.mithra.AccountFinder;
import com.pravder.money.db.mithra.Balance;
import java.math.BigDecimal;
import javax.ws.rs.WebApplicationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class TransactionResourceTest {

  private static TransactionResource transactionResource = new TransactionResource();
  private static MithraTestResource mithraTestResource;

  @BeforeAll
  public static void setUp() {
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
  public void testDepositAmountThrowsExceptionIfBadRequest() {
    WebApplicationException exception = Assertions.assertThrows(
        WebApplicationException.class,
        () -> transactionResource.depositAmount("123"),
        "Expected exception here");

    Assertions.assertEquals(MessageType.INVALID_REQUEST.getMessage(),
        exception.getMessage());
  }

  @Test
  public void testDepositRequestSubmissionSuccessful() {
    TransactionRequest transactionRequest = new TransactionRequest("acct_123",
        new Money(BigDecimal.valueOf(100.99), "USD"));
    String token = transactionResource.depositRequest(transactionRequest);
    Assertions.assertNotNull(token, "Expected not null request reference");
  }

  @Test
  public void testDepostSuccessful() {
    Account account = AccountFinder.findOne(AccountFinder.accountId().eq("acct_1"));
    BigDecimal initialBalance = account.getAccountBalanceForCurrency("USD").getBalance();

    TransactionRequest transactionRequest = new TransactionRequest("acct_1",
        new Money(BigDecimal.valueOf(100.99), "USD"));
    String token = transactionResource.depositRequest(transactionRequest);
    transactionResource.depositAmount(token);
    Balance finalBalance = account.getAccountBalanceForCurrency("USD");

    Assertions.assertEquals(100.99, finalBalance.getBalance().subtract(initialBalance).floatValue(), 0.5);
  }

  @Test
  public void testWithdrawRequestSubmissionSuccessful() {
    TransactionRequest transactionRequest = new TransactionRequest("acct_123",
        new Money(BigDecimal.valueOf(100.99), "USD"));
    String token = transactionResource.withdrawRequest(transactionRequest);
    Assertions.assertNotNull(token, "Expected not null request reference");
  }

  @Test
  public void testWithdrawSuccessful() {
    Account account = AccountFinder.findOne(AccountFinder.accountId().eq("acct_1"));
    BigDecimal initialBalance = account.getAccountBalanceForCurrency("USD").getBalance();

    TransactionRequest transactionRequest = new TransactionRequest("acct_1",
        new Money(BigDecimal.valueOf(100.99), "USD"));
    String token = transactionResource.withdrawRequest(transactionRequest);
    transactionResource.withdrawAmount(token);
    Balance finalBalance = account.getAccountBalanceForCurrency("USD");

    Assertions.assertEquals(100.99,
        initialBalance.subtract(finalBalance.getBalance()).floatValue(), 0.005);
  }

  @Test
  public void testWithdrawFailedDueToInsufficientFunds() {
    Account account = AccountFinder.findOne(AccountFinder.accountId().eq("acct_1"));
    BigDecimal initialBalance = account.getAccountBalanceForCurrency("USD").getBalance();

    TransactionRequest transactionRequest = new TransactionRequest("acct_1",
        new Money(BigDecimal.valueOf(10000.00), "USD"));
    String token = transactionResource.withdrawRequest(transactionRequest);


    WebApplicationException exception = Assertions.assertThrows(
        WebApplicationException.class,
        () -> transactionResource.withdrawAmount(token),
        "Expected exception here");

    Assertions.assertEquals(MessageType.INSUFFICIENT_FUNDS.getMessage(),
        exception.getMessage());
  }

  @Test
  public void testWithdrawAmountThrowsExceptionIfBadRequest() {
    WebApplicationException exception = Assertions.assertThrows(
        WebApplicationException.class,
        () -> transactionResource.withdrawAmount("123"),
        "Expected exception here");

    Assertions.assertEquals(MessageType.INVALID_REQUEST.getMessage(),
        exception.getMessage());
  }

  @Test
  public void testTransferRequestSubmissionSuccessful() {
    TransferRequest transferRequest = new TransferRequest("acct_1",
        "acct_2",
        new Money(BigDecimal.valueOf(100.99), "USD"));
    String token = transactionResource.transferRequest(transferRequest);
    Assertions.assertNotNull(token, "Expected not null request reference");
  }

  @Test
  public void testTransferSuccessful() {
    Account fromAccount = AccountFinder.findOne(AccountFinder.accountId().eq("acct_1"));
    BigDecimal initialBalanceFromAcct  = fromAccount.getAccountBalanceForCurrency("USD").getBalance();
    Account toAccount = AccountFinder.findOne(AccountFinder.accountId().eq("acct_2"));
    BigDecimal initialBalanceToAcct = toAccount.getAccountBalanceForCurrency("USD").getBalance();

    assertEqualsWithinThreshold(BigDecimal.valueOf(200.00), initialBalanceFromAcct);
    assertEqualsWithinThreshold(BigDecimal.valueOf(500.00), initialBalanceToAcct);

    TransferRequest transferRequest = new TransferRequest("acct_1",
        "acct_2",
        new Money(BigDecimal.valueOf(100.00), "USD"));
    String token = transactionResource.transferRequest(transferRequest);
    transactionResource.transferAmount(token);

    BigDecimal finalBalanceFromAcct  = fromAccount.getAccountBalanceForCurrency("USD").getBalance();
    BigDecimal finalBalanceToAcct = toAccount.getAccountBalanceForCurrency("USD").getBalance();

    assertEqualsWithinThreshold(BigDecimal.valueOf(100.00), finalBalanceFromAcct);
    assertEqualsWithinThreshold(BigDecimal.valueOf(600.00), finalBalanceToAcct);
  }

  @Test
  public void testTransferThrowsExceptionIfBadRequest() {
    WebApplicationException exception = Assertions.assertThrows(
        WebApplicationException.class,
        () -> transactionResource.transferAmount("123"),
        "Expected exception here");

    Assertions.assertEquals(MessageType.INVALID_REQUEST.getMessage(),
        exception.getMessage());
  }

  private void assertEqualsWithinThreshold(BigDecimal one, BigDecimal two)
  {
    Assertions.assertEquals(one.floatValue(), two.floatValue(), 0.005);
  }
}
