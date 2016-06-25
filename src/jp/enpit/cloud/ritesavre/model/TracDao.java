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
	 * milestoneに設定されたdueタイムをunixtime(秒単位)で取得する
	 * @param milestone
	 * @return
	 */
	public long getDueTime(String milestone){
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
		//TODO 引数をMilestoneにする
		long due =0;
		due = this.getDueTime(milestone);

		//calc default initial effort
		return (int) (member * (due-start)/60);
	}

	/**
	 * 対象マイルストーンの指定された時刻の時点で未終了の全チケットの見積時間を返す
	 * @deprecated
	 * @param ci
	 * @return
	 */
	public int getRemainedTaskEfforts(ChartInput ci){
		logger.info("TracDao.getRemainedTaskEfforts start:" + ci.getUnixtime());
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

	/**
	 * 対象マイルストーンの指定された時刻の時点で未終了の全チケットの見積時間を返す
	 * プロジェクト終了後にチャートを表示する際に利用されることを想定している
	 * @param ci
	 * @return
	 */
	public int getRemainedTaskEffortsAfterProject(ChartInput ci){
		logger.info("TracDao.getRemainedTaskEffortsAfterProject start:" + ci.getUnixtime());
		Integer rtEfforts;
		SqlSession session = sqlSessionFactory.openSession();

		try{
			rtEfforts = session.selectOne("Trac.getRemainedTaskEffortsAfterProject",ci);
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
