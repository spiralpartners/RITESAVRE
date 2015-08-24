package jp.enpit.cloud.ritesavre.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class FluentdLogModelTest {

	@Test
	public void testGetAccessUsers() {
		FluentdLogModel fm = new FluentdLogModel();
		fm.getAccessUsers();
	}

}
