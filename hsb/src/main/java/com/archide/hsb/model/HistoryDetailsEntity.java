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
@Table(name = "historydetails")
public class HistoryDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HistoryDetailsId")
	private int historyDeailsId;
	@Column(name = "HistoryGuid")
	private String historyUUID;
	@Column(name = "ItemDetails",columnDefinition = "LONGTEXT")
	private String itemDetails;
	@ManyToOne
	@JoinColumn(name = "HistoryId",referencedColumnName="HistoryId")
	private HistoryEntity historyEntity;
	
	public int getHistoryDeailsId() {
		return historyDeailsId;
	}
	public void setHistoryDeailsId(int historyDeailsId) {
		this.historyDeailsId = historyDeailsId;
	}
	public String getHistoryUUID() {
		return historyUUID;
	}
	public void setHistoryUUID(String historyUUID) {
		this.historyUUID = historyUUID;
	}
	public String getItemDetails() {
		return itemDetails;
	}
	public void setItemDetails(String itemDetails) {
		this.itemDetails = itemDetails;
	}
	public HistoryEntity getHistoryEntity() {
		return historyEntity;
	}
	public void setHistoryEntity(HistoryEntity historyEntity) {
		this.historyEntity = historyEntity;
	}
	@Override
	public String toString() {
		return "HistoryDetailsEntity [historyDeailsId=" + historyDeailsId + ", historyUUID=" + historyUUID
				+ ", itemDetails=" + itemDetails + ", historyEntity=" + historyEntity + "]";
	}
	
	
	
	

}
