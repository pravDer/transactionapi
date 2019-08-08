package com.pravder.money;

import com.gs.fw.common.mithra.MithraManager;
import com.gs.fw.common.mithra.MithraManagerProvider;
import com.pravder.money.db.mithra.Account;
import com.pravder.money.db.mithra.Balance;
import com.pravder.money.resources.AccountResource;
import com.pravder.money.resources.TransactionResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TransactionAppApplication extends Application<TransactionAppConfiguration> {

  public static void main(final String[] args) throws Exception {
    new TransactionAppApplication().run(args);
  }

  @Override
  public String getName() {
    return "transactionApp";
  }

  @Override
  public void initialize(final Bootstrap<TransactionAppConfiguration> bootstrap) {
    // TODO: application initialization
    initMithra();
  }

  @Override
  public void run(final TransactionAppConfiguration configuration,
                  final Environment environment) {
    //initMithra();
    environment.jersey().register(new AccountResource());
    environment.jersey().register(new TransactionResource());
  }

  private void initMithra() {
    MithraManager mithraManager = MithraManagerProvider.getMithraManager();
    mithraManager.setTransactionTimeout(120);
    try (InputStream is = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("ReladomoRuntimeConfig.xml")) {
      mithraManager.readConfiguration(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
    createTempData();
  }

  private void createTempData() {
    Account account = new Account();
    account.setAccountId("acct_123");
    account.setName("John");
    account.setAddress("Bangalore");
    account.setEmail("abc@gmail.com");
    account.setBaseCcy("USD");
    account.insert();

    Account account2 = new Account();
    account2.setAccountId("acct_456");
    account2.setName("Dave");
    account2.setAddress("Delhi");
    account2.setEmail("dave@gmail.com");
    account2.setBaseCcy("USD");
    account2.insert();

    Balance balance = new Balance();
    balance.setAccountId("acct_123");
    balance.setBalance(BigDecimal.valueOf(1000.99));
    balance.setCurrency("USD");
    balance.setBusinessDate(Timestamp.valueOf(LocalDateTime.now()));
    balance.insert();

    Balance balance2 = new Balance();
    balance2.setAccountId("acct_456");
    balance2.setBalance(BigDecimal.valueOf(5000.99));
    balance2.setCurrency("USD");
    balance2.setBusinessDate(Timestamp.valueOf(LocalDateTime.now()));
    balance2.insert();

  }

}
