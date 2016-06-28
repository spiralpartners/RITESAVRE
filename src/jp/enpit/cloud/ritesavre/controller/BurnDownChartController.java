package jp.enpit.cloud.ritesavre.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

import jp.enpit.cloud.ritesavre.model.TracDao;
import jp.enpit.cloud.ritesavre.mybatis.MyBatisConnectionFactory;

public class BurnDownChartController {

	private Logger logger;

	public BurnDownChartController() {
		logger = Logger.getLogger(getClass().getName());
	}


	/**
	 * あとで別コントローラに移動
	 * @return
	 */
	public ArrayList<String> listMilestones(String project) {
		ArrayList<String> milestones;
		//TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory("133.1.236.176", project));
		TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory(project,project));
		milestones = tDao.getMilestoneList();

		Collections.sort(milestones);
		return milestones;
	}

}
