package com.pravder.money.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a response containing account information.
 */
public class AccountResponse {

  @JsonProperty
  private String accountid;

  @JsonProperty
  private String name;

  @JsonProperty
  private String emailId;

  @JsonProperty
  private String address;

  @JsonCreator
  public AccountResponse(@JsonProperty("accountid") String accountid,
                         @JsonProperty("name") String name,
                         @JsonProperty("emailId") String emailId,
                         @JsonProperty("address") String address) {
    this.accountid = accountid;
    this.name = name;
    this.emailId = emailId;
    this.address = address;
  }

  public String getAccountid() {
    return accountid;
  }

  public String getName() {
    return name;
  }

  public String getEmailId() {
    return emailId;
  }

  public String getAddress() {
    return address;
  }

  public static class Builder {
    String accountId;
    String name;
    String emailId;
    String address;

    public Builder() {
    }

    public Builder setAccountId(String accountId) {
      this.accountId = accountId;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setEmailId(String emailId) {
      this.emailId = emailId;
      return this;
    }

    public Builder setAddress(String address) {
      this.address = address;
      return this;
    }

    public AccountResponse build() {
      return new AccountResponse(this.accountId, this.name, this.emailId, this.address);
    }
  }
}
