package jp.enpit.cloud.ritesavre.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import jp.enpit.cloud.ritesavre.model.Milestone;
import jp.enpit.cloud.ritesavre.model.MilestoneModel;
import jp.enpit.cloud.ritesavre.model.MilestoneNotDefinedException;
import jp.enpit.cloud.ritesavre.model.SbdchartModel;
import jp.enpit.cloud.ritesavre.model.TracDao;
import jp.enpit.cloud.ritesavre.mybatis.MyBatisConnectionFactory;
import jp.enpit.cloud.ritesavre.view.BurnDownChartEntity;
import jp.enpit.cloud.ritesavre.view.MilestoneForm;
import jp.enpit.cloud.ritesavre.view.PointEntity;

public class BurnDownChartInProjectController {

	private Logger logger;

	public BurnDownChartInProjectController() {
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * プロジェクト実行中にチケットが入力されるケースで、BDCを表示することを想定（lego,eventspiralプロジェクト）。
	 * プロジェクト名とマイルストーン名を引数に取り、日時と残見積もり工数のペアを持つ理想開始点、理想終了点と
	 * 日時および残見積もり工数のペアのリストとしてActualPointsを含むBurnDownChartEntityを返す。
	 * 正しくバーンダウンチャートを表示するためには，mongodbのritesavre db内のmilestone collに
	 * MilestoneFormの値が入力されている必要がある
	 *
	 * @param msf プロジェクト名とマイルストーン名
	 * @return
	 * @throws MilestoneNotDefinedException
	 * @throws SQLException
	 */
	public BurnDownChartEntity execute(MilestoneForm msf) throws MilestoneNotDefinedException{
		logger.info("BDCInProjectController.execute");
		/**
		 */
		MilestoneModel msm = new MilestoneModel();
		Milestone ms = msm.getMilestone(msf.getProject(), msf.getMilestone());

		//TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory("133.1.236.176", msf.getProject()));
		TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory(msf.getProject(), msf.getProject()));
		ms.setMilestoneDue(tDao.getDueTime(msf.getMilestone()));

		BurnDownChartEntity bdc_entity = new BurnDownChartEntity();

		if(ms.getMilestoneStart() == 0){
			throw new MilestoneNotDefinedException("開始時刻がマイルストーンに設定されていません");
		}
		//開発者の数と開始・終了時刻で理想線を引く
		long end = tDao.getDueTime(msf.getMilestone());
		//milestoneのdueにまだなっていない場合は現在時刻をendとする
		//Date.getTime()ではUnixtimeのミリ秒単位が返ってくるので、1000で割っておく
		Date now = new Date();
		if (now.getTime() / 1000 < end) {
			end = now.getTime() / 1000;
		}
		PointEntity e = new PointEntity(0, end);
		bdc_entity.setIdealEndPoint(e);
		int seffort = tDao.getDefaultInitialTaskEffort(msf.getMilestone(), ms.getMilestoneStart(),end,ms.getMember());
		PointEntity s = new PointEntity(seffort, ms.getMilestoneStart());
		bdc_entity.setIdealBeginPoint(s);

		//mongodb.ritesavre.sbdchartに登録されている点(開始～終了の範囲で)を取得する
		SbdchartModel schart = new SbdchartModel();
		ArrayList<PointEntity> a = schart.getActualPoints(ms);
		bdc_entity.setActualPoints(a);

		return bdc_entity;
	}

	public static void main(String[] args) throws MilestoneNotDefinedException {
		BurnDownChartInProjectController bdcipc = new BurnDownChartInProjectController();
		MilestoneForm msf = new MilestoneForm();
		msf.setProject("trac_lego");
		msf.setMilestone("lego1");
		bdcipc.execute(msf);

	}

}
