package jp.enpit.cloud.ritesavre.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import jp.enpit.cloud.ritesavre.controller.BurnDownChartController;
import jp.enpit.cloud.ritesavre.view.BurnDownChartEntity;
import jp.enpit.cloud.ritesavre.view.MilestoneForm;
import jp.enpit.cloud.ritesavre.view.PointEntity;

import org.junit.Test;

public class BurnDownChartControllerTest {

	
	@Test
	public void testListMilestones01() throws Exception {
		BurnDownChartController bdcc = new BurnDownChartController();
		ArrayList<String> milestones = bdcc.listMilestones("trac_apple");
		//System.out.println(milestones.size());
		assertEquals(5, milestones.size());
		assertEquals("apple1", milestones.get(0));
	}

	@Test
	public void testExecute02() throws Exception {
		BurnDownChartController bdcc = new BurnDownChartController();
		//bdcc.setProject("trac_legoPrep");
		MilestoneForm msf = new MilestoneForm();
		msf.setMilestone("apple1");
		msf.setProject("trac_apple");
		BurnDownChartEntity bdce = bdcc.execute(msf);
		System.out.println("start: " + bdce.getIdealBeginPoint());
		System.out.println("end: " + bdce.getIdealEndPoint());
		int i = 1;
		for (PointEntity p: bdce.getActualPoints()) {
			System.out.println(i++ + ": " + p);
		}
	}

	public void testExecute01() throws Exception {
		BurnDownChartController bdcc = new BurnDownChartController();
		//bdcc.setProject("trac_legoPrep");
		MilestoneForm msf = new MilestoneForm();
		msf.setMilestone("lego0");
		msf.setProject("trac_legoPrep");
		BurnDownChartEntity bdce = bdcc.execute(msf);
		System.out.println("start: " + bdce.getIdealBeginPoint());
		System.out.println("end: " + bdce.getIdealEndPoint());
		int i = 1;
		for (PointEntity p: bdce.getActualPoints()) {
			System.out.println(i++ + ": " + p);
		}
	}

}
