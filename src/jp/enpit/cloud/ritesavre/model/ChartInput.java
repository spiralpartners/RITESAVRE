package jp.enpit.cloud.ritesavre.model;

public class ChartInput {
	private String milestone;
	private Long start;
	public String getMilestone() {
		return milestone;
	}
	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public String toString(){
		return "milestone:" + milestone + " start:" + start;
	}


}
