package jp.enpit.cloud.ritesavre.view;

/**
 * タスク見積結果
 * @author igaki
 *
 */
public class TaskEstimation {
	int ticketId;
	double totalhours;
	double estimatedhours;
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public double getTotalhours() {
		return totalhours;
	}
	public void setTotalhours(double totalhours) {
		this.totalhours = totalhours;
	}
	public double getEstimatedhours() {
		return estimatedhours;
	}
	public void setEstimatedhours(double estimatedhours) {
		this.estimatedhours = estimatedhours;
	}
	

}
