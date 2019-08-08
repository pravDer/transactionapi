package com.pravder.money.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Represents a response containing transaction details.
 */
public class TransactionResponse {

  @JsonProperty
  String accountId;

  @JsonProperty
  String counterParty;

  @JsonProperty
  BigDecimal amount;

  @JsonProperty
  String source;

  @JsonProperty
  String currency;

  @JsonProperty
  Timestamp processingTime;

  @JsonProperty
  String comment;

  @JsonCreator
  public TransactionResponse(String accountId, String counterParty, BigDecimal amount,
                             String source, String currency, Timestamp processingTime,
                             String comment) {
    this.accountId = accountId;
    this.counterParty = counterParty;
    this.amount = amount;
    this.source = source;
    this.currency = currency;
    this.processingTime = processingTime;
    this.comment = comment;
  }

  public static class Builder {

    String accountId;
    String counterParty;
    BigDecimal amount;
    String source;
    String currency;
    Timestamp processingTime;
    String comment;

    public Builder setAccountId(String accountId) {
      this.accountId = accountId;
      return this;
    }

    public Builder setCounterParty(String counterParty) {
      this.counterParty = counterParty;
      return this;
    }

    public Builder setAmount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public Builder setSource(String source) {
      this.source = source;
      return this;
    }

    public Builder setProcessingTime(Timestamp processingTime) {
      this.processingTime = processingTime;
      return this;
    }

    public Builder setCurrency(String currency) {
      this.currency = currency;
      return this;
    }

    public TransactionResponse build() {
      return new TransactionResponse(this.accountId, this.counterParty, this.amount, this.source,
          this.currency, this.processingTime, this.comment);
    }
  }
}
