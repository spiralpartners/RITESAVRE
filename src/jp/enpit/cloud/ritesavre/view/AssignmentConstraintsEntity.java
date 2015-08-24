package jp.enpit.cloud.ritesavre.view;

import java.util.ArrayList;
import java.util.List;

public class AssignmentConstraintsEntity {

	/**
	 * 作成（ソース）等の総合計とAssignmentEntity(ユーザごとの担当数)を保持するBean
	 */
	List<AssignmentEntity> asEntityList = new ArrayList<AssignmentEntity>();
	int createSourceSum;
	int createUTestSum;
	int createITestSum;
	int reviewSum;
	int debugSourceSum;
	int debugUTestSum;
	int debugITestSum;
	int iTestSum;
	int otherTaskSum;
	public List<AssignmentEntity> getAsEntityList() {
		return asEntityList;
	}
	public void setAsEntityList(List<AssignmentEntity> asEntityList) {
		this.asEntityList = asEntityList;
	}
	public int getCreateSourceSum() {
		return createSourceSum;
	}
	public void setCreateSourceSum(int createSourceSum) {
		this.createSourceSum = createSourceSum;
	}
	public int getCreateUTestSum() {
		return createUTestSum;
	}
	public void setCreateUTestSum(int createUTestSum) {
		this.createUTestSum = createUTestSum;
	}
	public int getCreateITestSum() {
		return createITestSum;
	}
	public void setCreateITestSum(int createITestSum) {
		this.createITestSum = createITestSum;
	}
	public int getReviewSum() {
		return reviewSum;
	}
	public void setReviewSum(int reviewSum) {
		this.reviewSum = reviewSum;
	}
	public int getDebugSourceSum() {
		return debugSourceSum;
	}
	public void setDebugSourceSum(int debugSourceSum) {
		this.debugSourceSum = debugSourceSum;
	}
	public int getDebugUTestSum() {
		return debugUTestSum;
	}
	public void setDebugUTestSum(int debugUTestSum) {
		this.debugUTestSum = debugUTestSum;
	}
	public int getDebugITestSum() {
		return debugITestSum;
	}
	public void setDebugITestSum(int debugITestSum) {
		this.debugITestSum = debugITestSum;
	}
	public int getiTestSum() {
		return iTestSum;
	}
	public void setiTestSum(int iTestSum) {
		this.iTestSum = iTestSum;
	}
	public int getOtherTaskSum() {
		return otherTaskSum;
	}
	public void setOtherTaskSum(int otherTaskSum) {
		this.otherTaskSum = otherTaskSum;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(AssignmentEntity a : asEntityList) {
			sb.append(a + "\n");
		}
		return sb.toString();
	}



}
