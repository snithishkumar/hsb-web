package com.archide.mobilepay.json;

import java.util.ArrayList;
import java.util.List;

public class PurchaseStatus {
	
	private List<HistoryPurchaseData> historyData = new ArrayList<>();
	
	public List<HistoryPurchaseData> getHistoryData() {
		return historyData;
	}
	public void setHistoryData(List<HistoryPurchaseData> historyData) {
		this.historyData = historyData;
	}
	
	@Override
	public String toString() {
		return "PurchaseStatus [historyData=" + historyData + "]";
	}
	

}
