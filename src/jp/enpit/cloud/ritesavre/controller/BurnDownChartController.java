package jp.enpit.cloud.ritesavre.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Logger;

import jp.enpit.cloud.ritesavre.model.Milestone;
import jp.enpit.cloud.ritesavre.model.MilestoneModel;
import jp.enpit.cloud.ritesavre.model.MilestoneNotDefinedException;
import jp.enpit.cloud.ritesavre.model.TracModel;
import jp.enpit.cloud.ritesavre.util.TracDBUtils;
import jp.enpit.cloud.ritesavre.view.BurnDownChartEntity;
import jp.enpit.cloud.ritesavre.view.MilestoneForm;
import jp.enpit.cloud.ritesavre.view.PointEntity;

public class BurnDownChartController {

	private Logger logger;

	public BurnDownChartController() {
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * 正しくバーンダウンチャートを表示するためには，mongodbのritesavre db内のmilestone collに
	 * MilestoneFormの値が入力されている必要がある ->そのうちMongoDB使わないように変更したい
	 * また，Tracの該当プロジェクトにおいて，対象マイルストーンの締め切りが入力されていないとチャートの
	 * 開始日が1970年になる
	 * @param msf
	 * @return
	 */
	public BurnDownChartEntity execute(MilestoneForm msf) {
		BurnDownChartEntity bdc_entity = new BurnDownChartEntity();
		Connection conn = null;

		MilestoneModel msm = new MilestoneModel();
		long start;
		String project;
		int member;
		try {
			Milestone ms = msm.getMilestone(msf.getMilestone());
			start = ms.getMilestoneStart();
			project = ms.getProject();
			member = ms.getMember();
			if(project==null){
				project = msf.getProject();
			}
		} catch (MilestoneNotDefinedException e1) {
			start = 0;
			project = msf.getProject();
			member = 0;
		}

		try {

			conn = TracDBUtils.getConnection(project);
			TracModel trac = new TracModel(conn);
			if(start == 0){
				start = trac.getStartTime(msf.getMilestone());
			}
			int seffort = trac.getDefaultInitialTaskEffort(msf.getMilestone(), start, member);
			PointEntity s = new PointEntity(seffort, start);
			bdc_entity.setIdealBeginPoint(s);
			long end = trac.getEndTime(msf.getMilestone());
			PointEntity e = new PointEntity(0, end);
			bdc_entity.setIdealEndPoint(e);

			bdc_entity.addActualPoints(s);//実績値の切片追加
			Date now = new Date();
			if (now.getTime() * 1000 < end) {
				end = now.getTime() * 1000;
			}
			if (start == 0){
				start = end;
			}
			//System.out.println(start + ":" + end);
			//10分毎にplot
			for (long t = start + 10 * 60 * 1000 * 1000; t < end; t += 10 * 60 * 1000 * 1000) {
				logger.info("t = " + t + ", end = " + end);
				int reffort = trac.getRemainedTaskEffort(msf.getMilestone(), t, end);
				PointEntity r = new PointEntity(reffort, t);
				bdc_entity.addActualPoints(r);
			}
			int lasteffort = trac.getRemainedTaskEffort(msf.getMilestone(), end, end);
			PointEntity last = new PointEntity(lasteffort, end);
			bdc_entity.addActualPoints(last);

		} catch (SQLException e) {
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		return bdc_entity;
	}



	/**
	 * あとで別コントローラに移動
	 * @return
	 */
	public ArrayList<String> listMilestones(String project) {
		ArrayList<String> milestones;
		Connection conn = null;
		try {
			conn = TracDBUtils.getConnection(project);
			TracModel trac = new TracModel(conn);
			milestones = trac.getMilestoneList();
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
			milestones = new ArrayList<String>();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		Collections.sort(milestones);
		return milestones;
	}

}
