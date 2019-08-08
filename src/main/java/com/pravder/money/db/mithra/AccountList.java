package com.pravder.money.db.mithra;
import com.gs.fw.finder.Operation;
import com.pravder.money.api.AccountResponse;

import java.util.*;
public class AccountList extends AccountListAbstract
{
	public AccountList()
	{
		super();
	}

	public AccountList(int initialSize)
	{
		super(initialSize);
	}

	public AccountList(Collection c)
	{
		super(c);
	}

	public AccountList(Operation operation)
	{
		super(operation);
	}

	public List<AccountResponse> getAccounts()
	{
		List<AccountResponse> accounts = new ArrayList<>(this.size());
		for(com.pravder.money.db.mithra.Account account : this)
		{
			AccountResponse account1 = new AccountResponse.Builder()
					           .setAccountId(account.getAccountId())
					           .setName(account.getName())
					           .setEmailId(account.getEmail())
					           .setAddress(account.getAddress())
					           .build();
			accounts.add(account1);
		}
		return accounts;
	}
}
