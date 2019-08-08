package com.pravder.money.db.mithra;

import java.sql.Timestamp;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Balance extends BalanceAbstract {

  private Lock lock = new ReentrantLock();

  public Balance() {
    super();
    // You must not modify this constructor. Mithra calls this internally.
    // You can call this constructor. You can also add new constructors.
  }

  public Lock getLock()
  {
    return this.lock;
  }
}
