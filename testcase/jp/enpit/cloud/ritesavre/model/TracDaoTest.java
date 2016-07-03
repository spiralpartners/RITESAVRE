package jp.enpit.cloud.ritesavre.model;

import java.util.ArrayList;

import org.junit.Test;

import jp.enpit.cloud.ritesavre.mybatis.MyBatisConnectionFactory;

public class TracDaoTest {

	@Test
	public void testGetEndTime(){
		TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory("trac_EventSpiral","trac_EventSpiral"));
		Long end = tDao.getDueTime("Sprint1st");
		System.out.println("end:" + end);


	}

	@Test
	public void testGetRemainedTaskEfforts(){
		TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory("trac_EventSpiral","trac_EventSpiral"));
		ChartInput ci = new ChartInput();
		ci.setMilestone("Sprint1st");
		long t = 1439800200000000L;
		ci.setUnixtime(t);

		int lasteffort = tDao.getRemainedTaskEfforts(ci);

		System.out.println("effort:" + lasteffort);

	}

	@Test
	public void testGetReviewedComponentList(){
		TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory("trac_EventSpiral","trac_EventSpiral"));
		ArrayList<NotReviewedComponent> nrComponents = tDao.getNotReviewedComponentList();
		for(NotReviewedComponent c:nrComponents){
			System.out.println(c);

		}

	}

}
