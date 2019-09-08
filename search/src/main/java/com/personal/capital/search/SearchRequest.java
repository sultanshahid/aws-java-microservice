package com.personal.capital.search;

public class SearchRequest {
	String planName;
	String sponsorName;
	String sponsorState;
	
	public SearchRequest() {
	}
	
	public SearchRequest(String planName, String sponsorName, String sponsorState) {
		this.planName = planName;
		this.sponsorName = sponsorName;
		this.sponsorState = sponsorState;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getSponsorState() {
		return sponsorState;
	}

	public void setSponsorState(String sponsorState) {
		this.sponsorState = sponsorState;
	}

}