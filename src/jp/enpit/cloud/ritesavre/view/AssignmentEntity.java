package jp.enpit.cloud.ritesavre.view;


public class AssignmentEntity {
	String owner;
	int createSourceNum;
	int createUTestNum;
	int createITestNum;
	int reviewNum;
	int debugSourceNum;
	int debugUTestNum;
	int debugITestNum;
	int iTestNum;
	int otherTaskNum;

	double createSourceTimeNum;
	double createUTestTimeNum;
	double createITestTimeNum;
	double reviewTimeNum;
	double debugSourceTimeNum;
	double debugUTestTimeNum;
	double debugITestTimeNum;
	double iTestTimeNum;
	double otherTaskTimeNum;

	public AssignmentEntity(){
		this.owner = "";
	}

	
	public double getCreateSourceTimeNum() {
		return createSourceTimeNum;
	}


	public void setCreateSourceTimeNum(double createSourceTimeNum) {
		this.createSourceTimeNum = createSourceTimeNum;
	}


	public double getCreateUTestTimeNum() {
		return createUTestTimeNum;
	}


	public void setCreateUTestTimeNum(double createUTestTimeNum) {
		this.createUTestTimeNum = createUTestTimeNum;
	}


	public double getCreateITestTimeNum() {
		return createITestTimeNum;
	}


	public void setCreateITestTimeNum(double createITestTimeNum) {
		this.createITestTimeNum = createITestTimeNum;
	}


	public double getReviewTimeNum() {
		return reviewTimeNum;
	}


	public void setReviewTimeNum(double reviewTimeNum) {
		this.reviewTimeNum = reviewTimeNum;
	}


	public double getDebugSourceTimeNum() {
		return debugSourceTimeNum;
	}


	public void setDebugSourceTimeNum(double debugSourceTimeNum) {
		this.debugSourceTimeNum = debugSourceTimeNum;
	}


	public double getDebugUTestTimeNum() {
		return debugUTestTimeNum;
	}


	public void setDebugUTestTimeNum(double debugUTestTimeNum) {
		this.debugUTestTimeNum = debugUTestTimeNum;
	}


	public double getDebugITestTimeNum() {
		return debugITestTimeNum;
	}


	public void setDebugITestTimeNum(double debugITestTimeNum) {
		this.debugITestTimeNum = debugITestTimeNum;
	}


	public double getiTestTimeNum() {
		return iTestTimeNum;
	}


	public void setiTestTimeNum(double iTestTimeNum) {
		this.iTestTimeNum = iTestTimeNum;
	}


	public double getOtherTaskTimeNum() {
		return otherTaskTimeNum;
	}


	public void setOtherTaskTimeNum(double otherTaskTimeNum) {
		this.otherTaskTimeNum = otherTaskTimeNum;
	}


	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getCreateSourceNum() {
		return createSourceNum;
	}
	public void setCreateSourceNum(int createSourceNum) {
		this.createSourceNum = createSourceNum;
	}
	public int getCreateUTestNum() {
		return createUTestNum;
	}
	public void setCreateUTestNum(int createUTestNum) {
		this.createUTestNum = createUTestNum;
	}
	public int getCreateITestNum() {
		return createITestNum;
	}
	public void setCreateITestNum(int createITestNum) {
		this.createITestNum = createITestNum;
	}
	public int getReviewNum() {
		return reviewNum;
	}
	public void setReviewNum(int reviewNum) {
		this.reviewNum = reviewNum;
	}
	public int getDebugSourceNum() {
		return debugSourceNum;
	}
	public void setDebugSourceNum(int debugSourceNum) {
		this.debugSourceNum = debugSourceNum;
	}
	public int getDebugUTestNum() {
		return debugUTestNum;
	}
	public void setDebugUTestNum(int debugUTestNum) {
		this.debugUTestNum = debugUTestNum;
	}
	public int getDebugITestNum() {
		return debugITestNum;
	}
	public void setDebugITestNum(int debugITestNum) {
		this.debugITestNum = debugITestNum;
	}
	public int getiTestNum() {
		return iTestNum;
	}
	public void setiTestNum(int iTestNum) {
		this.iTestNum = iTestNum;
	}
	public int getOtherTaskNum() {
		return otherTaskNum;
	}
	public void setOtherTaskNum(int otherTaskNum) {
		this.otherTaskNum = otherTaskNum;
	}


	public String toString() {
		return String.format(
				"{owner:%s, createSource:%d, createUTest:%d, createITest:%d, review:%d, debugSource:%d," +
				"debugUTest:%d, debugITest:%d, iTest:%d, otherTask:%d}",
				owner, createSourceNum, createUTestNum, createITestNum,reviewNum,
				debugSourceNum, debugUTestNum, debugITestNum, iTestNum, otherTaskNum);
	}
}
