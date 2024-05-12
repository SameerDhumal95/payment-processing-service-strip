package com.cpt.payments.dao;

import com.cpt.payments.dto.TransactionLog;

public interface TransactionLogDao {
	public void createTransactionLog(TransactionLog transactionLog);
	
	//public TransactionLog updateTransactionLog(int transactionId, String fromStatus, String toStatus);

}
