package jp.enpit.cloud.ritesavre.model;

import java.util.Date;

public class NotReviewedComponent {
	private String component;
	private String milestone;
	private String type;
	private Date changetime;

	public String getMilestone() {
		return milestone;
	}
	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getChangetime() {
		return changetime;
	}
	public void setChangetime(Date changetime) {
		this.changetime = changetime;
	}
	public String toString(){
		return "milestone;"+this.milestone + " component:" + component + " changetime:" + changetime;

	}


}
