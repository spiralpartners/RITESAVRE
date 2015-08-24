package jp.enpit.cloud.ritesavre.model;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class MileStoneModelTest {

	@Test
	public void testRegisterMilestoneDateTime() throws MilestoneNotDefinedException {
		MilestoneModel msm = new MilestoneModel();

		long milestoneStart = 100000;
		msm.registerMilestoneDateTime("test",milestoneStart);

		Assert.assertEquals(milestoneStart, msm.getMilestone("test").getMilestoneStart());

	}

	@Test
	public void testRegisterMilestone() throws MilestoneNotDefinedException {
		MilestoneModel msm = new MilestoneModel();

		long milestoneStart = 100000;
		String project = "project";
		msm.registerMilestone("test", project, milestoneStart);

		Assert.assertEquals(milestoneStart, msm.getMilestone("test").getMilestoneStart());

	}

}
