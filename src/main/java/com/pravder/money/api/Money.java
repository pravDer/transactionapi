package com.pravder.money.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;


public class Money {

  @JsonProperty("balance")
  @NotNull
  private BigDecimal balance;

  @JsonProperty("currency")
  @NotNull
  private String currency;

  @JsonCreator
  public Money(@JsonProperty("balance") BigDecimal balance,
               @JsonProperty("currency") String currency) {
    this.balance = balance;
    this.currency = currency;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public String getCurrency() {
    return currency;
  }

}
