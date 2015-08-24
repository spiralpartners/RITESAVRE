package jp.enpit.cloud.ritesavre.model;

public class Ticket {
	int id;
	long time;
	String component;
	String milestone;
	String summary;
	double totalhours;
	
	public Ticket(){
		this.component="";
		this.milestone="";
		this.summary="";
	}
	public Ticket(int id, long time, String component, String milestone, String summary){
		this.id = id;
		this.time = time;
		this.component = component;
		this.milestone = milestone;
		this.summary = summary;
	}

	public Ticket(int id, long time, String component, String milestone, String summary, double totalhours){
		this.id = id;
		this.time = time;
		this.component = component;
		this.milestone = milestone;
		this.summary = summary;
		this.totalhours = totalhours;
	}

	
	public double getTotalhours() {
		return totalhours;
	}
	public void setTotalhours(double totalhours) {
		this.totalhours = totalhours;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getMilestone() {
		return milestone;
	}
	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}


}
