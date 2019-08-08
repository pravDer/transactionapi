package com.pravder.money.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Represents a request to transfer money from one account to another.
 */
public class TransferRequest implements Request {

  @JsonProperty("fromAccountNumber")
  @NotNull
  private String fromAccountNumber;

  @JsonProperty("toAccountNumber")
  @NotNull
  private String toAccountNumber;

  @JsonProperty("money")
  @NotNull
  private Money money;

  @JsonCreator
  public TransferRequest(@JsonProperty("fromAccountNumber") String fromAccountNumber,
                         @JsonProperty("toAccountNumber") String toAccountNumber,
                         @JsonProperty("money") Money money) {
    this.fromAccountNumber = fromAccountNumber;
    this.toAccountNumber = toAccountNumber;
    this.money = money;
  }

  public String getFromAccountNumber() {
    return fromAccountNumber;
  }

  public String getToAccountNumber() {
    return toAccountNumber;
  }

  public Money getMoney() {
    return money;
  }

}
