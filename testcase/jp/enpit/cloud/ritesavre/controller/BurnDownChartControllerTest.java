package jp.enpit.cloud.ritesavre.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import jp.enpit.cloud.ritesavre.view.BurnDownChartEntity;
import jp.enpit.cloud.ritesavre.view.MilestoneForm;
import jp.enpit.cloud.ritesavre.view.PointEntity;

public class BurnDownChartControllerTest {


	@Test
	public void testListMilestones01() throws Exception {
		BurnDownChartController bdcc = new BurnDownChartController();
		ArrayList<String> milestones = bdcc.listMilestones("trac_EventSpiral");
		//System.out.println(milestones.size());
		assertEquals(5, milestones.size());
		assertEquals("preSprint", milestones.get(0));
	}

	@Test
	public void testExecute02() throws Exception {
		BurnDownChartController bdcc = new BurnDownChartController();
		//bdcc.setProject("trac_legoPrep");
		MilestoneForm msf = new MilestoneForm();
		msf.setMilestone("Sprint1st");
		msf.setProject("trac_EventSpiral");
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
