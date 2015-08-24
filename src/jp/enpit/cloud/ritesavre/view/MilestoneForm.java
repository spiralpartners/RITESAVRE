package jp.enpit.cloud.ritesavre.view;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author igaki
 *
 */
public class MilestoneForm  extends AbstractForm {
	
	@NotEmpty(message="milestone名を指定してください")
	private String milestone;

	@NotEmpty(message="project名を指定してください")
	private String project;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getMilestone() {
		return milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}
	
	

}
