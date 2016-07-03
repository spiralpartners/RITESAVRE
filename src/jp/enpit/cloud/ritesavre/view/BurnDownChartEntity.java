package jp.enpit.cloud.ritesavre.view;

import java.util.ArrayList;
import java.util.List;


public class BurnDownChartEntity extends AbstractEntity {

	private PointEntity idealBeginPoint;
	private PointEntity idealEndPoint;
	private ArrayList<PointEntity> actualPoints;

	public BurnDownChartEntity() {
		actualPoints = new ArrayList<PointEntity>();
	}
	public PointEntity getIdealBeginPoint() {
		return idealBeginPoint;
	}
	public void setIdealBeginPoint(PointEntity idealBeginPoint) {
		this.idealBeginPoint = idealBeginPoint;
	}
	public PointEntity getIdealEndPoint() {
		return idealEndPoint;
	}
	public void setIdealEndPoint(PointEntity idealEndPoint) {
		this.idealEndPoint = idealEndPoint;
	}
	public ArrayList<PointEntity> getActualPoints() {
		return actualPoints;
	}
	public void setActualPoints(ArrayList<PointEntity> actualPoints) {
		this.actualPoints = actualPoints;
	}
	public void addActualPoints(PointEntity actualPoint) {
		this.actualPoints.add(actualPoint);
	}

}
