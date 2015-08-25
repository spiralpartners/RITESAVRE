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


}
