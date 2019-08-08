package com.pravder.money.resources;

public enum MessageType {
  INVALID_REQUEST("Not a valid request"),
  DUPLICATE_REQUEST("Duplicate Request"),
  INSUFFICIENT_FUNDS("Not sufficient funds");

  private String messageDetails;

  MessageType(String messageDetails) {
    this.messageDetails = messageDetails;
  }

  public String getMessage() {
    return this.messageDetails;
  }

}
