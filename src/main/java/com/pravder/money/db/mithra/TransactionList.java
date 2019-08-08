package com.pravder.money.db.mithra;
import com.gs.fw.finder.Operation;
import java.util.*;
public class TransactionList extends TransactionListAbstract
{
	public TransactionList()
	{
		super();
	}

	public TransactionList(int initialSize)
	{
		super(initialSize);
	}

	public TransactionList(Collection c)
	{
		super(c);
	}

	public TransactionList(Operation operation)
	{
		super(operation);
	}
}
