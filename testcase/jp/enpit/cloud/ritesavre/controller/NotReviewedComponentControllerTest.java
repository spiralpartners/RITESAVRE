package jp.enpit.cloud.ritesavre.controller;

import java.util.ArrayList;

import org.junit.Test;

import jp.enpit.cloud.ritesavre.view.MilestoneForm;
import jp.enpit.cloud.ritesavre.view.NotReviewedComponentEntity;

public class NotReviewedComponentControllerTest {

	@Test
	public void testExecute() {
		NotReviewedComponentController nrcc = new NotReviewedComponentController();
		MilestoneForm msf = new MilestoneForm();
		msf.setMilestone("Sprint1st");
		msf.setProject("trac_EventSpiral");
		ArrayList<NotReviewedComponentEntity> nrcEntityList = nrcc.execute(msf);
		for(NotReviewedComponentEntity e:nrcEntityList){
			System.out.println(e);
		}

	}

}
