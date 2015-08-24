package jp.enpit.cloud.ritesavre.controller;

import java.util.List;

import jp.enpit.cloud.ritesavre.view.MilestoneForm;
import jp.enpit.cloud.ritesavre.view.TaskEstimation;

import org.junit.Test;

public class DisplayEstimationAccuracyControllerTest {

	@Test
	public void testExecute() {
		DisplayEstimationAccuracyController deac = new DisplayEstimationAccuracyController();
		MilestoneForm msf = new MilestoneForm();
		msf.setProject("trac_EventSpiral");
		msf.setMilestone("preSprint");
		List<TaskEstimation> taskList =	deac.execute(msf);

	}

}
