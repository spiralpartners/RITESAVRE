package jp.enpit.cloud.ritesavre.view;


public class MilestoneInfoForm {
	private String milestone;//milestone name on trac
	private String project;//project name ex.trac_lego
	private long milestoneStart;//16桁のUnixtime 例：1403676600000000 通常の10桁のものに下6桁0を追加すれば良い．
	private int member;//number of developers mongoにも明示的にNumberInt()で登録しないとDoubleになるので怒られる

	public MilestoneInfoForm() {
		this.milestone = "";
	}

	public String getProject(){
		return this.project;
	}
	public void setProject(String project){
		this.project = project;
	}

	public String getMilestone() {
		return milestone;
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
