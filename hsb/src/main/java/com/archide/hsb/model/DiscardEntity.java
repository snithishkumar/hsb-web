package com.archide.hsb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.archide.mobilepay.enumeration.DiscardBy;

@Entity
@Table(name = "discard")
public class DiscardEntity {
	
	public static final String PAYMENT_DETAILS = "paymentDetails";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DiscardId")
	private int discardId;
	@Column(name = "Reason")
	private String reason;
	@Column(name = "DiscardBy")
	@Enumerated(EnumType.STRING)
	private DiscardBy discardBy;
	@Column(name = "CreatedDateTime")
	private long createdDateTime;
	
	@ManyToOne
	@JoinColumn(name = "PaymentDetailsId",referencedColumnName="PaymentDetailsId")
	private PaymentDetails paymentDetails;
	
	public int getDiscardId() {
		return discardId;
	}
	public void setDiscardId(int discardId) {
		this.discardId = discardId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public DiscardBy getDiscardBy() {
		return discardBy;
	}
	public void setDiscardBy(DiscardBy discardBy) {
		this.discardBy = discardBy;
	}
	public long getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	@Override
	public String toString() {
		return "DiscardEntity [discardId=" + discardId + ", reason=" + reason + ", discardBy=" + discardBy
				+ ", createdDateTime=" + createdDateTime + "]";
	}
	
	

}
