package jp.enpit.cloud.ritesavre.controller;

import jp.enpit.cloud.ritesavre.view.AssignmentConstraintsEntity;

import org.junit.Test;

public class DisplayAssignmentListControllerTest {

	@Test
	public void testExecute() {
		DisplayAssignmentListController dalc = new DisplayAssignmentListController();
		AssignmentConstraintsEntity asc = dalc.execute("trac_EventSpiral");
		System.out.println(asc);
	}

}
