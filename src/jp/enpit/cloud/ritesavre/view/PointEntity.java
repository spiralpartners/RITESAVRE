package jp.enpit.cloud.ritesavre.view;

import java.util.Date;

public class PointEntity {

	private int estimatedEffort;
	private Date elapsedTime;

	public PointEntity(int estimatedEffort, long elapsedTime) {
		this.estimatedEffort = estimatedEffort;
		this.elapsedTime = new Date(elapsedTime / 1000);
	}
	public int getEstimatedEffort() {
		return estimatedEffort;
	}
	public void setEstimatedEffort(int estimatedEffort) {
		this.estimatedEffort = estimatedEffort;
	}
	public Date getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(Date elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String toString() {
		return elapsedTime.toString() + ", " + String.valueOf(estimatedEffort);
	}

}
