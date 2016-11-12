package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "paymentdetails")
public class PaymentDetails {
	
	public static final String PAYMENT_DETAILS_ID = "paymentDetailsId";
	public static final String PAYMENT_DETAILS_UUID = "paymentDetailsUUID";
	public static final String HISTORY = "history";
	public static final String AMOUNT = "amount";
	public static final String PAYED_AMOUNT = "payedAmount";
	public static final String TRANSACTION_ID = "transactionId";
	public static final String PAYMENT_DATE_TIME = "paymentDateTime";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PaymentDetailsId")
	private int paymentDetailsId;
	@Column(name = "PaymentDetailsUuid")
	private String paymentDetailsUUID;
	@ManyToOne
	@JoinColumn(name = "HistoryId", referencedColumnName = "HistoryId")
	private History history;
	@Column(name = "Amount")
	private double amount;
	@Column(name = "PayedAmount")
	private double payedAmount;
	@Column(name = "TransactionId")
	private String transactionId;
	@Column(name = "PaymentDateTime")
	private long paymentDateTime;

	public int getPaymentDetailsId() {
		return paymentDetailsId;
	}

	public void setPaymentDetailsId(int paymentDetailsId) {
		this.paymentDetailsId = paymentDetailsId;
	}

	public String getPaymentDetailsUUID() {
		return paymentDetailsUUID;
	}

	public void setPaymentDetailsUUID(String paymentDetailsUUID) {
		this.paymentDetailsUUID = paymentDetailsUUID;
	}

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPayedAmount() {
		return payedAmount;
	}

	public void setPayedAmount(double payedAmount) {
		this.payedAmount = payedAmount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public long getPaymentDateTime() {
		return paymentDateTime;
	}

	public void setPaymentDateTime(long paymentDateTime) {
		this.paymentDateTime = paymentDateTime;
	}

	@Override
	public String toString() {
		return "PaymentDetails [paymentDetailsId=" + paymentDetailsId + ", paymentDetailsUUID=" + paymentDetailsUUID
				+ ", history=" + history + ", amount=" + amount + ", payedAmount=" + payedAmount + ", transactionId="
				+ transactionId + ", paymentDateTime=" + paymentDateTime + "]";
	}

}
