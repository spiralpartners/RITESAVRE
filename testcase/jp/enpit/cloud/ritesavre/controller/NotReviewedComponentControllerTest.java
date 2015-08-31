package jp.enpit.cloud.ritesavre.controller;

import java.util.ArrayList;

import org.junit.Test;

import jp.enpit.cloud.ritesavre.view.NotReviewedComponentEntity;

public class NotReviewedComponentControllerTest {

	@Test
	public void testExecute() {
		NotReviewedComponentController nrcc = new NotReviewedComponentController();
		ArrayList<NotReviewedComponentEntity> nrcEntityList = nrcc.execute("trac_EventSpiral");
		for(NotReviewedComponentEntity e:nrcEntityList){
			System.out.println(e);
		}

	}

}
