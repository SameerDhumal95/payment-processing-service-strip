package com.cpt.payments.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.TransactionStatusEnum;
import com.cpt.payments.service.TransactionStatusHandler;
import com.cpt.payments.service.impl.status.handler.CreatedTransactionStatusHandler;
import com.cpt.payments.service.impl.status.handler.InitiatedTransactionStatusHandler;
import com.cpt.payments.service.impl.status.handler.PendingTransactionStatusHandler;

@Component
public class TransactionStatusFactory {

//	private static final Logger LOGGER = LogManager.getLogger(TransactionStatusFactory.class);

	@Autowired
	private ApplicationContext context;

//	@Autowired
//	CreatedTransactionStatusHandler createdTransactionStatusHandler;

	public TransactionStatusHandler getStatusHandler(TransactionStatusEnum transactionStatusEnum) {
		System.out.println("fetching transaction status handler for -> " + transactionStatusEnum);
		switch (transactionStatusEnum) {
		case CREATED:
			return context.getBean(CreatedTransactionStatusHandler.class);
		case INITIATED:
			return context.getBean(InitiatedTransactionStatusHandler.class);
		case PENDING:	
			return context.getBean(PendingTransactionStatusHandler.class);
		
		default:
			return null;
		}
	}

}
