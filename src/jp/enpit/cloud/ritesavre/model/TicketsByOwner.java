package jp.enpit.cloud.ritesavre.model;

import java.util.ArrayList;
import java.util.List;

public class TicketsByOwner {
	String owner;
	List<Ticket> createSource;
	List<Ticket> createUTest;
	List<Ticket> createITest;
	List<Ticket> review;
	List<Ticket> debugSource;
	List<Ticket> debugUTest;
	List<Ticket> debugITest;
	List<Ticket> iTest;
	List<Ticket> otherTask;

	public TicketsByOwner(){
		this.owner="";
		this.createSource = new ArrayList<Ticket>();
		this.createUTest = new ArrayList<Ticket>();
		this.createITest = new ArrayList<Ticket>();
		this.review = new ArrayList<Ticket>();
		this.debugSource = new ArrayList<Ticket>();
		this.debugUTest = new ArrayList<Ticket>();
		this.debugITest = new ArrayList<Ticket>();
		this.iTest = new ArrayList<Ticket>();
		this.otherTask = new ArrayList<Ticket>();
	}

	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public List<Ticket> getCreateSource() {
		return createSource;
	}
	public void setCreateSource(List<Ticket> createSource) {
		this.createSource = createSource;
	}
	public List<Ticket> getCreateUTest() {
		return createUTest;
	}
	public void setCreateUTest(List<Ticket> createUTest) {
		this.createUTest = createUTest;
	}
	public List<Ticket> getCreateITest() {
		return createITest;
	}
	public void setCreateITest(List<Ticket> createITest) {
		this.createITest = createITest;
	}
	public List<Ticket> getReview() {
		return review;
	}
	public void setReview(List<Ticket> review) {
		this.review = review;
	}
	public List<Ticket> getDebugSource() {
		return debugSource;
	}
	public void setDebugSource(List<Ticket> debugSource) {
		this.debugSource = debugSource;
	}
	public List<Ticket> getDebugUTest() {
		return debugUTest;
	}
	public void setDebugUTest(List<Ticket> debugUTest) {
		this.debugUTest = debugUTest;
	}
	public List<Ticket> getDebugITest() {
		return debugITest;
	}
	public void setDebugITest(List<Ticket> debugITest) {
		this.debugITest = debugITest;
	}
	public List<Ticket> getiTest() {
		return iTest;
	}
	public void setiTest(List<Ticket> iTest) {
		this.iTest = iTest;
	}
	public List<Ticket> getOtherTask() {
		return otherTask;
	}
	public void setOtherTask(List<Ticket> otherTask) {
		this.otherTask = otherTask;
	}

}
