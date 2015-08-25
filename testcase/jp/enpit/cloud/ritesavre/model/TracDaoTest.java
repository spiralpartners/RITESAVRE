package jp.enpit.cloud.ritesavre.model;

import org.junit.Test;

import jp.enpit.cloud.ritesavre.mybatis.MyBatisConnectionFactory;

public class TracDaoTest {

	@Test
	public void testGetEndTime(){
		TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory("trac_EventSpiral"));
		Long end = tDao.getEndTime("Sprint1st");
		System.out.println("end:" + end);


	}

}
