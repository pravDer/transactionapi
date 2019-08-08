package com.pravder.money.db.mithra;
import com.gs.fw.common.mithra.finder.Operation;

import java.sql.Timestamp;
public class Account extends AccountAbstract
{
	public Account()
	{
		super();
		// You must not modify this constructor. Mithra calls this internally.
		// You can call this constructor. You can also add new constructors.
	}


	//todo: consider making return Optional
	public static Account getAccountForId(String accountId)
	{
		Operation accountOperation  = AccountFinder.accountId().eq(accountId);
		return AccountFinder.findOne(accountOperation);
	}
}
