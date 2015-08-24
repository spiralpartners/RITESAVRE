package jp.enpit.cloud.ritesavre.model;


public class Milestone {
	private String milestone;
	private String project;
	private long milestoneStart;
	private int member;

	public String getMilestone() {
		return milestone;
	}
	public void setProject(String project){
		this.project = project;
	}
	public String getProject(){
		return this.project;
	}
	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}
	public long getMilestoneStart() {
		return milestoneStart;
	}
	public void setMilestoneStart(long milestoneStart) {
		this.milestoneStart = milestoneStart;
	}
	public int getMember() {
		return member;
	}
	public void setMember(int member) {
		this.member = member;
	}



}
