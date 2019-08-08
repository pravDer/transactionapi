package com.pravder.money.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Represent a request to do a self transaction e.g. withdraw
 */
public class TransactionRequest implements Request {

  @JsonProperty("accountNumber")
  @NotNull
  private String accountNumber;

  @JsonProperty("money")
  @NotNull
  private Money money;

  @JsonCreator
  public TransactionRequest(@JsonProperty("accountNumber") String accountNumber,
                            @JsonProperty("money") Money money) {
    this.accountNumber = accountNumber;
    this.money = money;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public Money getMoney() {
    return money;
  }

}
