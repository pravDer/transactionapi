package com.pravder.money.resources;

import com.gs.fw.common.mithra.MithraManagerProvider;
import com.gs.fw.common.mithra.TransactionalCommand;
import com.pravder.money.api.Money;
import com.pravder.money.api.Request;
import com.pravder.money.api.TransactionRequest;
import com.pravder.money.api.TransferRequest;
import com.pravder.money.db.mithra.Account;
import com.pravder.money.db.mithra.Balance;
import com.pravder.money.db.mithra.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;



/**
 * Provides functionality to process transactions.
 */
final class TransactionProcessor {

  Logger logger = LoggerFactory.getLogger(TransactionProcessor.class);
  private static Map<String, Request> transactionRequests = new HashMap<>();

  public static void submitRequest(String token, Request request) {
    transactionRequests.put(token, request);
  }

  public static boolean processWithdrawals(String token) {
    boolean isProcessed;
    TransactionRequest request = (TransactionRequest) transactionRequests.get(token);
    Account account = Account.getAccountForId(request.getAccountNumber());
    Balance balance = account.getAccountBalanceForCurrency(request.getMoney().getCurrency());
    if (null != balance && !hasSufficentBalanceForWithdrawal(account, request.getMoney())) {
      throw new WebApplicationException(MessageType.INSUFFICIENT_FUNDS.getMessage(),
          Response.Status.PRECONDITION_FAILED);
    }
    balance.setBalance(balance.getBalance().subtract(request.getMoney().getBalance()));
    isProcessed = true;
    transactionRequests.remove(token);
    return isProcessed;
  }


  public static boolean processDeposits(String token) {
    boolean isProcessed;
    TransactionRequest request = (TransactionRequest) transactionRequests.get(token);
    Account account = Account.getAccountForId(request.getAccountNumber());
    Balance balance = account.getAccountBalanceForCurrency(request.getMoney().getCurrency());
    if (null == balance) {
      balance = new Balance();
      balance.setAccountId(account.getAccountId());
      balance.setBalance(request.getMoney().getBalance());
      balance.setCurrency(request.getMoney().getCurrency());
      balance.setBusinessDate(Timestamp.valueOf(LocalDateTime.now()));
      balance.insert();
    } else {
      balance.setBalance(balance.getBalance().add(request.getMoney().getBalance()));
    }
    isProcessed = true;
    transactionRequests.remove(token);
    return isProcessed;

  }

  public static boolean processTransfers(String token) {
    boolean isProcessed = false;
    TransferRequest request = (TransferRequest) transactionRequests.get(token);
    Account fromAccount = Account.getAccountForId(request.getFromAccountNumber());
    if (hasSufficentBalanceForWithdrawal(fromAccount, request.getMoney())) {
      Account toAccount = Account.getAccountForId(request.getToAccountNumber());
      String currency = request.getMoney().getCurrency();
      Balance fromAccountBalance = fromAccount.getAccountBalanceForCurrency(currency);
      Balance toAccountBalance = toAccount.getAccountBalanceForCurrency(currency);
      Transaction transaction = getTransaction(request);

      MithraManagerProvider.getMithraManager().executeTransactionalCommand(
          (TransactionalCommand<Transaction>) mithraTransaction -> {
            fromAccountBalance.setBalance(fromAccountBalance.getBalance().subtract(request.getMoney().getBalance()));
            toAccountBalance.setBalance(toAccountBalance.getBalance().add(request.getMoney().getBalance()));
            transaction.insert();
            return null;
          });

      isProcessed = true;
      transactionRequests.remove(token);
    }
    return isProcessed;
  }

  private static Transaction getTransaction(TransferRequest request) {
    Transaction transaction = new Transaction();
    transaction.setAccountId(request.getFromAccountNumber());
    transaction.setAmount(request.getMoney().getBalance());
    transaction.setBusinessDate(Timestamp.valueOf(LocalDateTime.now()));
    transaction.setComment("Dummy Comment; to implement");
    transaction.setCounterParty(request.getToAccountNumber());
    transaction.setCurrency(request.getMoney().getCurrency());
    transaction.setSource("Dummy source; to implememt");
    return transaction;
  }

  public static boolean isRequestPresent(String token) {

    return transactionRequests.containsKey(token);
  }

  private static boolean hasSufficentBalanceForWithdrawal(Account account,
                                                          Money money) {
    Balance balance = account.getAccountBalanceForCurrency(money.getCurrency());
    return null != balance && balance.getBalance().compareTo(money.getBalance()) >= 0;
  }
}
