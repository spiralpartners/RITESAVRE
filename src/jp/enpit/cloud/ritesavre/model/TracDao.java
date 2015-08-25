package jp.enpit.cloud.ritesavre.model;

import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class TracDao {
	private SqlSessionFactory sqlSessionFactory;
	private Logger logger;

	public TracDao(SqlSessionFactory sqlSessionFactory){
		this.logger = Logger.getLogger(getClass().getName());
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public long getStartTime(String milestone){
		logger.info("TracDao.getStartTime");
		long start = 0;
		SqlSession session = sqlSessionFactory.openSession();

		try{
			start = session.selectOne("Trac.getStartTime",milestone);
		}finally{
			session.close();
		}
		System.out.println("start:"+start);

		return start;

	}

	public long getEndTime(String milestone){
		logger.info("TracDao.getEndTime");
		long end = 0;
		SqlSession session = sqlSessionFactory.openSession();

		try{
			end = session.selectOne("Trac.getEndTime",milestone);
		}finally{
			session.close();
		}
		System.out.println("start:"+end);

		return end;

	}
	public int getDefaultInitialTaskEffort(String milestone, long start,  int member){
		long due =0;
		due = this.getEndTime(milestone);

		//calc default initial effort
		return (int) (member * (due-start)/(60*1000*1000));
	}

	public int getRemainedTaskEfforts(ChartInput ci){
		logger.info("TracDao.getRemainedTaskEfforts");
		logger.info(ci.toString());
		//int rtEfforts = 0;
		Integer rtEfforts;
		SqlSession session = sqlSessionFactory.openSession();

		try{
			rtEfforts = session.selectOne("Trac.getRemainedTaskEfforts",ci);
		}finally{
			session.close();
		}
		if(rtEfforts == null){
			return 0;
		}else{
			return rtEfforts.intValue();
		}
	}

}
