package jp.enpit.cloud.ritesavre.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import jp.enpit.cloud.ritesavre.model.NotReviewedComponent;
import jp.enpit.cloud.ritesavre.model.TracDao;
import jp.enpit.cloud.ritesavre.mybatis.MyBatisConnectionFactory;
import jp.enpit.cloud.ritesavre.util.ConversionUtils;
import jp.enpit.cloud.ritesavre.view.NotReviewedComponentEntity;

public class NotReviewedComponentController {
	private Logger logger;

	public NotReviewedComponentController() {
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * レビューが終わっていないコンポーネントのリストをマイルストーンごとに返す．
	 * REST形式では下記のように呼ぶ
	 * http://localhost:8080/RITESAVRE/dwr/jsonp/NotReviewedComponentController/execute/trac_EventSpiral/
	 * @param project プロジェクト名(例：trac_EventSpiral)
	 * @return
	 */
	public ArrayList<NotReviewedComponentEntity> execute(String project){
		logger.info("NotReviewedComponentController.execute");

		//TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory("133.1.236.176", msf.getProject()));
		TracDao tDao = new TracDao(MyBatisConnectionFactory.getSqlSessionFactory(project,project));
		ArrayList<NotReviewedComponent> nrComponents = tDao.getNotReviewedComponentList();

		ArrayList<NotReviewedComponentEntity> nrcEntityList = new ArrayList<NotReviewedComponentEntity>();
		for(NotReviewedComponent c:nrComponents){
			NotReviewedComponentEntity e = new NotReviewedComponentEntity();
			ConversionUtils.convert(e, c);
			nrcEntityList.add(e);

		}


		return nrcEntityList;
	}

}
