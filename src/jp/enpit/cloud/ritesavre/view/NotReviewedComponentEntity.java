package jp.enpit.cloud.ritesavre.view;

public class NotReviewedComponentEntity {
	private String milestone;
	private String component;
	private String type;
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

	public String toString(){
		return "milestone:"+milestone+" component:"+component+" type:"+type;
	}


}
