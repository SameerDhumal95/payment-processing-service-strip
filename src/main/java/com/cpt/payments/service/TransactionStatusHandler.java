package com.cpt.payments.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cpt.payments.dao.TransactionLogDao;
import com.cpt.payments.dto.Transaction;
import com.cpt.payments.dto.TransactionLog;

public abstract class TransactionStatusHandler {
	
	@Autowired
	TransactionLogDao transactionLogDao;
	
	public abstract boolean updateStatus(Transaction transaction);
	
	protected void updateTransactionLog(int transactionId, String fromStatus, String toStatus) {
		System.out.println("updateTransactionLog | transactionId : " + transactionId + " fromStatus : " + fromStatus + " toStatus : " + toStatus );
		TransactionLog transactionLog = TransactionLog.builder()
				.transactionId(transactionId)
				.txnFromStatus(fromStatus)
				.txnToStatus(toStatus)
				.build();
		transactionLogDao.createTransactionLog(transactionLog);
	}

}
