package com.cpt.payments.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cpt.payments.dao.TransactionDao;
import com.cpt.payments.dto.Transaction;

@Repository
public class TransactionDaoImpl implements TransactionDao {
	
	@Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Transaction createTransaction(Transaction transaction) {
		// TODO Auto-generated method stub		
		
	        String sql = "INSERT INTO Transaction " +
	                "(userId, paymentMethodId, providerId, paymentTypeId, amount, currency, " +
	                "txnStatusId, txnReference, txnDetailsId, providerCode, providerMessage, " +
	                "debitorAccount, creditorAccount, providerReference, merchantTransactionReference, retryCount) " +
	                "VALUES " +
	                "(:userId, :paymentMethodId, :providerId, :paymentTypeId, :amount, :currency, " +
	                ":txnStatusId, :txnReference, :txnDetailsId, :providerCode, :providerMessage, " +
	                ":debitorAccount, :creditorAccount, :providerReference, :merchantTransactionReference, :retryCount)";

	        MapSqlParameterSource params = new MapSqlParameterSource();
	        params.addValue("userId", transaction.getUserId());
	        params.addValue("paymentMethodId", transaction.getPaymentMethodId());
	        params.addValue("providerId", transaction.getProviderId());
	        params.addValue("paymentTypeId", transaction.getPaymentTypeId());
	        params.addValue("amount", transaction.getAmount());
	        params.addValue("currency", transaction.getCurrency());
	        params.addValue("txnStatusId", transaction.getTxnStatusId());
	        params.addValue("txnReference", transaction.getTxnReference());
	        params.addValue("txnDetailsId", transaction.getTxnDetailsId());
	        params.addValue("providerCode", transaction.getProviderCode());
	        params.addValue("providerMessage", transaction.getProviderMessage());
	        params.addValue("debitorAccount", transaction.getDebitorAccount());
	        params.addValue("creditorAccount", transaction.getCreditorAccount());
	        params.addValue("providerReference", transaction.getProviderReference());
	        params.addValue("merchantTransactionReference", transaction.getMerchantTransactionReference());
	        params.addValue("retryCount", transaction.getRetryCount());

	        
	        try {
	        	 KeyHolder keyHolder = new GeneratedKeyHolder();
	        	
	        	int rowUpdate = jdbcTemplate.update(sql, params, keyHolder);
	        	
	        	Integer generatedId = keyHolder.getKey().intValue();
	        	
	        	transaction.setId(generatedId);
	        	
	        	System.out.println("Successfully Inserted data | rowUpdated : " + rowUpdate);
				
			} catch (Exception e) {
				System.out.println("Exception CreateTransaction : " + e);
				return null;
			}	        
		
		return transaction;
	}

	@Override
	public boolean updateTransaction(Transaction transaction) {
		System.out.println("Invoking updateTransaction");
		try {
//			System.out.println(updateTransaction());
			jdbcTemplate.update(updateTransaction(), new BeanPropertySqlParameterSource(transaction));
			return true;
		} catch (Exception e) {
			System.out.println("exception while updating TRANSACTION in DB :: " + transaction);
		}
		return false;		
	}
	
	private String updateTransaction() {
		StringBuilder queryBuilder = new StringBuilder("Update Transaction ");
		queryBuilder.append("SET txnStatusId=:txnStatusId, providerCode=:providerCode, providerMessage=:providerMessage ");
		queryBuilder.append("WHERE id=:id ");
		System.out.println(" " + "update Transaction query -> " + queryBuilder);
		return queryBuilder.toString();
	}

	@Override
	public Transaction getTransactionById(long transactionId) {
		System.out.println(" :: fetching Transaction Details  for :: " + transactionId);

		Transaction transaction = null;
		try {
			transaction = jdbcTemplate.queryForObject(getTransactionById(),
					new BeanPropertySqlParameterSource(Transaction.builder().id((int) transactionId).build()),
					new BeanPropertyRowMapper<>(Transaction.class));
			System.out.println(" :: transaction Details from DB  = " + transaction);
		} catch (Exception e) {
			System.out.println("unable to get transaction Details " + e);
		}
		return transaction;
	}
	private String getTransactionById() {
		StringBuilder queryBuilder = new StringBuilder("Select * from Transaction where id=:id ");
		System.out.println(" " + "getTransactionById query -> " + queryBuilder);
		return queryBuilder.toString();
	}

}
