package jp.enpit.cloud.ritesavre.model;

import java.util.ArrayList;
import java.util.List;
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

	public ArrayList<NotReviewedComponent> getNotReviewedComponentList(){
		logger.info("TracDao.getNotReviewedComponentList");
		List<NotReviewedComponent> nrComponents;
		SqlSession session = sqlSessionFactory.openSession();
		try{
			nrComponents = session.selectList("Trac.getNotReviewedComponentList");
		}finally{
			session.close();
		}
		return new ArrayList<NotReviewedComponent>(nrComponents);

	}

	/**
	 * そのマイルストーンで一番最初にacceptされた時刻を取得する
	 * @param milestone
	 * @return
	 */
	public long getStartTime(String milestone){
		logger.info("TracDao.getStartTime");
		Long start;
		SqlSession session = sqlSessionFactory.openSession();

		try{
			start = session.selectOne("Trac.getStartTime",milestone);
		}finally{
			session.close();
		}
		if(start == null){
			return 0L;
		}else{
			System.out.println("start:"+start);
			return start.longValue();
		}

	}

	/**
	 * milestoneに設定されたdueタイムを取得する
	 * @param milestone
	 * @return
	 */
	public long getEndTime(String milestone){
		logger.info("TracDao.getEndTime");
		Long end;
		SqlSession session = sqlSessionFactory.openSession();

		try{
			end = session.selectOne("Trac.getEndTime",milestone);
		}finally{
			session.close();
		}
		if(end == null){
			return 0L;
		}else{
			System.out.println("end:"+end);
			return end.longValue();
		}

	}

	/**
	 * 開始時刻とメンバー数から初期残工数を計算する．DBにはアクセスしない．
	 * @param milestone
	 * @param start
	 * @param member
	 * @return
	 */
	public int getDefaultInitialTaskEffort(String milestone, long start,  int member){
		long due =0;
		due = this.getEndTime(milestone);

		//calc default initial effort
		return (int) (member * (due-start)/(60*1000*1000));
	}

	/**
	 * 対象マイルストーンの指定された時刻の時点で未終了の全チケットの見積時間を返す
	 * @param ci
	 * @return
	 */
	public int getRemainedTaskEfforts(ChartInput ci){
		logger.info("TracDao.getRemainedTaskEfforts start:" + ci.getStart());
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

	public ArrayList<String> getMilestoneList(){
		logger.info("TracDao.getMilestoneList");
		List<String> milestones;
		SqlSession session = sqlSessionFactory.openSession();
		try{
			milestones = session.selectList("Trac.getMilestoneList");
		}finally{
			session.close();
		}
		return new ArrayList<String>(milestones);

	}

}
