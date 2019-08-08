package com.pravder.money.resources;

import com.pravder.money.api.BalanceResponse;
import com.pravder.money.api.Money;
import com.pravder.money.api.TransactionResponse;
import com.pravder.money.db.mithra.Balance;
import com.pravder.money.db.mithra.BalanceList;
import com.pravder.money.db.mithra.Transaction;
import com.pravder.money.db.mithra.TransactionList;
import com.wordnik.swagger.annotations.Api;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Api to get details on a account.
 */
@Path("v1/account")
@Api(value = "/hello", description = "Operations on the hello object")
public class AccountResource {


  /**
   * Provides account details for a given account id.
   *
   * @param accountId Account Identifier
   * @return a account
   */
  @Path("/{accountId}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<BalanceResponse> getAccountBalance(@PathParam("accountId") String accountId) {
    com.pravder.money.db.mithra.Account account = com.pravder.money.db.mithra.Account
        .getAccountForId(accountId);
    BalanceList balances = account.getAccountBalance();
    List<BalanceResponse> balanceRespons = new ArrayList<>();
    for (Balance balance : balances) {
      balanceRespons.add(new BalanceResponse.Builder()
          .setAccountId(balance.getAccountId())
          .setName(account.getName())
          .setMoney(new Money(balance.getBalance(), balance.getCurrency()))
          .build());
    }
    return balanceRespons;
  }

  /**
   * Return balances for a account.
   *
   * @param accountId Account Identifier
   * @return Balances for the account
   */
  @Path("/{accountId}/balances")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<BalanceResponse> getBalances(@PathParam("accountId") String accountId) {
    com.pravder.money.db.mithra.Account account = com.pravder.money.db.mithra.Account
        .getAccountForId(accountId);
    BalanceList balances = account.getAccountBalance();
    List<BalanceResponse> balanceRespons = new ArrayList<>();
    for (Balance balance : balances) {
      balanceRespons.add(new BalanceResponse.Builder()
          .setAccountId(balance.getAccountId())
          .setName(account.getName())
          .setMoney(new Money(balance.getBalance(), balance.getCurrency()))
          .build());
    }
    return balanceRespons;

  }

  /**
   * Api to get transactions for an account.
   *
   * @param accountId Account Identifier
   * @return transactions for the account
   */
  @Path("/{accountId}/transactions")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<TransactionResponse> getTransactionsForAccount(@PathParam("accountId")
                                                                 String accountId) {
    com.pravder.money.db.mithra.Account account = com.pravder.money.db.mithra.Account
        .getAccountForId(accountId);
    TransactionList transactions = account.getAccountTransaction();
    List<TransactionResponse> transactionResponses = new ArrayList<>(transactions.size());
    for (Transaction transaction : transactions) {
      transactionResponses.add(new TransactionResponse.Builder()
          .setAccountId(transaction.getAccountId())
          .setCounterParty(transaction.getCounterParty())
          .setAmount(transaction.getAmount())
          .setCurrency(transaction.getCurrency())
          .setProcessingTime(transaction.getBusinessDate())
          .setSource(transaction.getSource())
          .build()
      );
    }
    return transactionResponses;
  }

}
