package com.pravder.money.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a response containing account with balances.
 */
public class BalanceResponse {

  @JsonProperty
  String accountid;

  @JsonProperty
  String name;

  @JsonProperty
  Money money;

  @JsonCreator
  public BalanceResponse(String accountid, String name,
                         Money money) {
    this.accountid = accountid;
    this.name = name;
    this.money = money;
  }

  public String getAccountid() {
    return accountid;
  }

  public String getName() {
    return name;
  }

  public Money getMoney() {
    return money;
  }

  public static class Builder {

    String accountid;
    String name;
    Money money;

    public Builder setAccountId(String accountId) {
      this.accountid = accountId;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setMoney(Money money) {
      this.money = money;
      return this;
    }

    public BalanceResponse build() {
      return new BalanceResponse(this.accountid, this.name, this.money);
    }
  }
}
