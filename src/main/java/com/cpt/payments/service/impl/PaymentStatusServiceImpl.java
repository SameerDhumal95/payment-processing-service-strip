package com.cpt.payments.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cpt.payments.constants.ErrorCodeEnum;
import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.dto.Transaction;
import com.cpt.payments.exception.PaymentProcessingException;
import com.cpt.payments.service.PaymentStatusService;
import com.cpt.payments.service.TransactionStatusHandler;
import com.cpt.payments.service.factory.TransactionStatusFactory;

@Service
public class PaymentStatusServiceImpl implements PaymentStatusService {
	
	@Autowired
	TransactionStatusFactory transactionStatusFactory;

	@Override
	public Transaction updatePaymentStatus(Transaction transaction) {
		System.out.println("Inovking service class : updatePaymentStatus");
		
		Integer txnStatusId = transaction.getTxnStatusId();		
		TransactionStatusEnum transactionStatusEnum = TransactionStatusEnum.getTransactionStatusEnum(txnStatusId);
		
		//factory
		TransactionStatusHandler statusHandler = transactionStatusFactory.getStatusHandler(transactionStatusEnum);
		if(statusHandler == null) {
			System.out.println(" invalid transaction handler -> " + transaction.getTxnStatusId());
			
			throw new PaymentProcessingException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodeEnum.TRANSACTION_STATUS_HANDLER_NOT_FOUND.getErrorCode(),
					ErrorCodeEnum.TRANSACTION_STATUS_HANDLER_NOT_FOUND.getErrorMessage());
		}
		
		boolean isUpdate = statusHandler.updateStatus(transaction);
		
		System.out.println("Status is updated | isUpdate : " + isUpdate +" | transactionStatusEnum : "+transactionStatusEnum);
		
		if(!isUpdate) {
			//TODO How to handle error in System..??
			System.out.println("FAILED to update transaction");
			System.out.println(" transaction status update failed -> " + transaction.getTxnStatusId());
			throw new PaymentProcessingException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodeEnum.TRANSACTION_STATUS_UPDATE_FAILED.getErrorCode(),
					ErrorCodeEnum.TRANSACTION_STATUS_UPDATE_FAILED.getErrorMessage());
		}
		
		System.out.println("Transaction updated Successfully");
		return transaction;
	}

}
