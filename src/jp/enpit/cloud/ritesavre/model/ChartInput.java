package jp.enpit.cloud.ritesavre.model;

public class ChartInput {
	private String milestone;
	private Long unixtime;
	public String getMilestone() {
		return milestone;
	}
	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}
	public Long getUnixtime() {
		return unixtime;
	}
	public void setUnixtime(Long start) {
		this.unixtime = start;
	}
	public String toString(){
		return "milestone:" + milestone + " start:" + unixtime;
	}


}
