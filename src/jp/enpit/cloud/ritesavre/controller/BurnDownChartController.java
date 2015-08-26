package jp.enpit.cloud.ritesavre.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Logger;

import jp.enpit.cloud.ritesavre.model.ChartInput;
import jp.enpit.cloud.ritesavre.model.Milestone;
import jp.enpit.cloud.ritesavre.model.MilestoneModel;
import jp.enpit.cloud.ritesavre.model.MilestoneNotDefinedException;
import jp.enpit.cloud.ritesavre.model.TracDao;
import jp.enpit.cloud.ritesavre.mybatis.MyBatisConnectionFactory;
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
	 * @throws SQLException
	 */
	public BurnDownChartEntity execute(MilestoneForm msf){
		BurnDownChartEntity bdc_entity = new BurnDownChartEntity();

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
			logger.info("MilestoneNotDefinedException");
			start = 0;
			project = msf.getProject();
			member = 0;
		}



		TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory(project));
		//startが設定されていない場合は，最初にアクセプトされたチケットの更新時刻を開始時刻とする
		if(start == 0){
			start = tDao.getStartTime(msf.getMilestone());
		}
		//開発者の数と開始・終了時刻で理想線を引く
		int seffort = tDao.getDefaultInitialTaskEffort(msf.getMilestone(), start, member);
		PointEntity s = new PointEntity(seffort, start);
		bdc_entity.setIdealBeginPoint(s);
		long end = tDao.getEndTime(msf.getMilestone());
		PointEntity e = new PointEntity(0, end);
		bdc_entity.setIdealEndPoint(e);

		//milestoneのdueにまだなっていない場合は現在時刻をendとする
		Date now = new Date();
		if (now.getTime() * 1000 < end) {
			end = now.getTime() * 1000;
		}
		/*			if (start == 0){
				start = end;
			}*/

		//System.out.println(start + ":" + end);
		//10分毎にplot
		ChartInput ci = new ChartInput();
		ci.setMilestone(msf.getMilestone());
		//end-startを50分割し，チャートの点が50より多くならないようにする
		long unit = (end - start)/50;

		//unitが10分より小さい場合は5分にあわせる（点が細かくなり過ぎないように）
		if(unit < 5 * 60 * 1000 * 1000){
			unit = 5 * 60 * 1000 * 1000;
		}
		for (long t = start; t < end; t += unit) {

			ci.setStart(t);
			int reffort = tDao.getRemainedTaskEfforts(ci);
			PointEntity r = new PointEntity(reffort, t);
			bdc_entity.addActualPoints(r);
		}
		ci.setStart(end);
		int lasteffort = tDao.getRemainedTaskEfforts(ci);
		PointEntity last = new PointEntity(lasteffort, end);
		bdc_entity.addActualPoints(last);

		return bdc_entity;
	}



	/**
	 * あとで別コントローラに移動
	 * @return
	 */
	public ArrayList<String> listMilestones(String project) {
		ArrayList<String> milestones;
		TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory(project));
		milestones = tDao.getMilestoneList();

		Collections.sort(milestones);
		return milestones;
	}

}
