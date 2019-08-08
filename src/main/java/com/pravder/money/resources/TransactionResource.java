package com.pravder.money.resources;

import com.pravder.money.api.TransactionRequest;
import com.pravder.money.api.TransferRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Api to perform different types of trasactions e.g. deposit, withdrawals and transfers
 */
@Path("/v1/transaction")
public class TransactionResource {

  /**
   * Send an intent for withdrawal from an account.
   *
   * @param transactionRequest object has details for the withdrawal
   * @return a unique token to be used for committing this request
   */
  @Path("/withdraw-intent")
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public String withdrawRequest(@Valid @NotNull TransactionRequest transactionRequest) {

    String token = TokenGeneratorUtility.getTokenForRequest(transactionRequest);
    if (TransactionProcessor.isRequestPresent(token)) {
      throw new WebApplicationException(MessageType.DUPLICATE_REQUEST.getMessage(),
          Response.Status.BAD_REQUEST);
    }
    TransactionProcessor.submitRequest(token, transactionRequest);
    return token;
  }

  /**
   * Commit the withdrawal intent; this will withdraw moeny from account.
   * @param token secret token required to commit on this withdrawal
   */
  @Path("/withdraw-intent/commit")
  @PUT
  public void withdrawAmount(String token) {
    if (!TransactionProcessor.isRequestPresent(token)) {
      throw new WebApplicationException(MessageType.INVALID_REQUEST.getMessage(),
          Response.Status.PRECONDITION_FAILED);
    }
    boolean requestProcessed = TransactionProcessor.processWithdrawals(token);
    if (!requestProcessed) {
      throw new WebApplicationException(MessageType.INSUFFICIENT_FUNDS.getMessage(),
          Response.Status.PRECONDITION_FAILED);
    }
  }

  /**
   * Send an intent for deposit to an account.
   * @param transactionRequest object has details for the deposit request
   * @return a unique token to be used for committing this request
   */
  @Path("/deposit-intent")
  @POST
  public String depositRequest(@Valid @NotNull TransactionRequest transactionRequest) {

    String token = TokenGeneratorUtility.getTokenForRequest(transactionRequest);
    if (TransactionProcessor.isRequestPresent(token)) {
      throw new WebApplicationException(MessageType.DUPLICATE_REQUEST.getMessage(),
          Response.Status.BAD_REQUEST);
    }
    TransactionProcessor.submitRequest(token, transactionRequest);
    return token;
  }

  /**
   * Commit the deposit intent; this will deposit moeny to account.
   * @param token secret token required to commit on this deposit request
   */
  @Path("/deposit-intent/commit")
  @PUT
  public void depositAmount(String token) {
    if (!TransactionProcessor.isRequestPresent(token)) {
      throw new WebApplicationException(MessageType.INVALID_REQUEST.getMessage(),
          Response.Status.PRECONDITION_FAILED);
    }
    boolean requestProcessed = TransactionProcessor.processDeposits(token);
    if (!requestProcessed) {
      throw new WebApplicationException(MessageType.INVALID_REQUEST.getMessage(),
          Response.Status.PRECONDITION_FAILED);
    }
  }

  /**
   * Send an intent to transfer money from one account to another.
   * @param transferRequest object has details for the transfer request
   * @return a unique token to be used for committing this request
   */
  @Path("/transfer-intent")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String transferRequest(@Valid @NotNull TransferRequest transferRequest) {

    String token = TokenGeneratorUtility.getTokenForRequest(transferRequest);
    if (TransactionProcessor.isRequestPresent(token)) {
      throw new WebApplicationException(MessageType.DUPLICATE_REQUEST.getMessage(),
          Response.Status.BAD_REQUEST);
    }
    TransactionProcessor.submitRequest(token, transferRequest);
    return token;
  }

  /**
   * Commit the transfer intent; this will transfer moeny from one account to another.
   * @param token secret token required to commit on this transfer request
   */
  @Path("/transfer-intent/commit")
  @PUT
  public void transferAmount(String token) {
    if (!TransactionProcessor.isRequestPresent(token)) {
      throw new WebApplicationException(MessageType.INVALID_REQUEST.getMessage(),
          Response.Status.PRECONDITION_FAILED);
    }
    boolean requestProcessed = TransactionProcessor.processTransfers(token);
    if (!requestProcessed) {
      throw new WebApplicationException(MessageType.INSUFFICIENT_FUNDS.getMessage(),
          Response.Status.PRECONDITION_FAILED);
    }
  }
}


