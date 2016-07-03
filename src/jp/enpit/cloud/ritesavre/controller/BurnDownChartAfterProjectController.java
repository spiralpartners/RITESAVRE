package jp.enpit.cloud.ritesavre.controller;

import java.sql.SQLException;
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

public class BurnDownChartAfterProjectController {

	private Logger logger;

	public BurnDownChartAfterProjectController() {
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * プロジェクト終了時にチケットが入力されるケースで、BDCを表示することを想定（trac_appleプロジェクト）。
	 * プロジェクト名とマイルストーン名を引数に取り、日時と残見積もり工数のペアを持つ
	 * 理想開始点、理想終了点と日時および残見積もり工数のペアのリストとしてActualPointsを含むBurnDownChartEntityを返す。
	 * 正しくバーンダウンチャートを表示するためには，mongodbのritesavre db内のmilestone collに
	 * MilestoneFormの値が入力されている必要がある
	 *
	 * @param msf プロジェクト名とマイルストーン名
	 * @return
	 * @throws MilestoneNotDefinedException
	 * @throws SQLException
	 */
	public BurnDownChartEntity execute(MilestoneForm msf) throws MilestoneNotDefinedException{
		/**
		 * TODO 必要な処理
		 * Done tracからマイルストーンの締め切りを取得
		 * Done mongoからメンバー数を取得
		 * Done mongoから開始時刻を取得
		 * Done 理想開始点、終了点を計算
		 * 時刻を入力するとその時点での残見積もり工数を取得するSQLを開始～終了まで3分刻みで取得する処理を実施
		 */
		//TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory("133.1.236.176", msf.getProject()));
		TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory(msf.getProject(), msf.getProject()));
		//System.out.println(tDao.getDueTime(msf.getMilestone()));

		MilestoneModel msm = new MilestoneModel();
		Milestone ms = msm.getMilestone(msf.getProject(), msf.getMilestone());
		System.out.println("Start:"+ms.getMilestoneStart());

		BurnDownChartEntity bdc_entity = new BurnDownChartEntity();

		if(ms.getMilestoneStart() == 0){
			throw new MilestoneNotDefinedException("マイルストーン開始時刻が管理者によって設定されていません");
		}
		//開発者の数と開始・終了時刻で理想線を引く
		long end = tDao.getDueTime(msf.getMilestone());
		//milestoneのdueにまだなっていない場合は現在時刻をendとする
		Date now = new Date();
		if(end == 0L){
			throw new MilestoneNotDefinedException("マイルストーン終了時刻がTracに設定されていません");
		}else if (now.getTime() / 1000 < end) {
			end = now.getTime() / 1000;
		}
		PointEntity e = new PointEntity(0, end);
		bdc_entity.setIdealEndPoint(e);
		int seffort = tDao.getDefaultInitialTaskEffort(msf.getMilestone(), ms.getMilestoneStart(),end,ms.getMember());
		PointEntity s = new PointEntity(seffort, ms.getMilestoneStart());
		bdc_entity.setIdealBeginPoint(s);

		ChartInput ci = new ChartInput();
		ci.setMilestone(ms.getMilestone());
		for(long t = ms.getMilestoneStart();t<end;t=t+60){
			ci.setUnixtime(t);
			int reffort = tDao.getRemainedTaskEffortsAfterProject(ci);
			PointEntity r = new PointEntity(reffort,t);
			bdc_entity.addActualPoints(r);
		}
		int lasteffort = tDao.getRemainedTaskEffortsAfterProject(ci);
		PointEntity last = new PointEntity(lasteffort, end);
		bdc_entity.addActualPoints(last);

		return bdc_entity;
	}

	public static void main(String[] args) throws MilestoneNotDefinedException {
		BurnDownChartAfterProjectController bdcapc = new BurnDownChartAfterProjectController();
		MilestoneForm msf = new MilestoneForm();
		msf.setProject("trac_apple");
		msf.setMilestone("apple1");
		bdcapc.execute(msf);

	}

}
