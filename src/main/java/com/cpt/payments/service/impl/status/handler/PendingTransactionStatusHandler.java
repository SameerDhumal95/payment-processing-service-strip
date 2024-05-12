package com.cpt.payments.service.impl.status.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.controller.PaymentController;
import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dao.TransactionLogDao;
import com.cpt.payments.dto.Transaction;
import com.cpt.payments.dto.TransactionLog;
import com.cpt.payments.service.TransactionStatusHandler;

@Component
public class PendingTransactionStatusHandler extends TransactionStatusHandler {
	
	
	
	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private TransactionLogDao transactionLogDao;

	@Override
	public boolean updateStatus(Transaction transaction) {
System.out.println(" transaction PENDING -> " + transaction);
		
		transaction.setTxnStatusId(TransactionStatusEnum.PENDING.getId());
		
		boolean transactionStatus = transactionDao.updateTransaction(transaction);
		
		if (!transactionStatus) {
			System.out.println(" updating transaction failed -> " + transaction);
			return false;
		}
		
//		TransactionLog transactionLog = TransactionLog.builder().transactionId(transaction.getId())
//				.txnFromStatus(TransactionStatusEnum.INITIATED.getName())
//				.txnToStatus(TransactionStatusEnum.PENDING.getName()).build();
//		transactionLogDao.createTransactionLog(transactionLog);
		
		updateTransactionLog(transaction.getId(), 
				TransactionStatusEnum.INITIATED.getName(), 
				TransactionStatusEnum.PENDING.getName());
		return true;
	}

}
