package jp.enpit.cloud.ritesavre.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import jp.enpit.cloud.ritesavre.util.DBUtils;
import jp.enpit.cloud.ritesavre.view.PointEntity;

public class SbdchartModel {

	private final String DB_SBDCHART_COLLECTION = "sbdchart";
	/**
	 * Loggerオブジェクト
	 */
	private Logger logger;
	/**
	 * DBオブジェクト
	 */
	private DB db;
	/**
	 * DBCollectionオブジェクト
	 */
	private DBCollection coll;
	/**
	 * getActualPointsで返す上限の数（これより多い点が登録されている場合は間引く）
	 */
	private final int POINT_ENTITY_NUM = 100;
	/**
	 * Logger、DB、DBCollectionフィールドに各オブジェクトを設定する．
	 */
		public SbdchartModel() {
		logger = Logger.getLogger(getClass().getName());
		db = DBUtils.getInstance().getDb();
		coll = db.getCollection(DB_SBDCHART_COLLECTION);

	}

	/**
	 * POINT_ENTITY_NUMの数だけ間引いて、mongoに登録されたチャートの点を返す
	 * @param m
	 * @return
	 */
	public ArrayList<PointEntity> getActualPoints(Milestone m){
		logger.info("SbdchartModel.getActualPoints");
		ArrayList<PointEntity> actualPoints = new ArrayList<PointEntity>();
		DBObject keys = new BasicDBObject();
		keys.put("project", m.getProject());
		keys.put("milestone", m.getMilestone());

		DBObject timearea = new BasicDBObject();
		timearea.put("$gte", m.getMilestoneStart());
		timearea.put("$lte", m.getMilestoneDue());
		keys.put("time", timearea);

		DBObject sortKey = new BasicDBObject();
		sortKey.put("time", 1);//昇順

		try{
			DBCursor cursor = coll.find(keys).sort(sortKey);
			//actualPointsの数がPOINT_ENTITY_NUMの数程度に収まるように間引きながらmongoから情報を取得する
			int count = cursor.count();
			double skip = 1;
			if(count>POINT_ENTITY_NUM) skip = (double)count/(double)POINT_ENTITY_NUM;
			for(int i=0;cursor.hasNext();i++){
				DBObject obj = cursor.next();
				if(i%(int)skip==0){
					PointEntity p = new PointEntity();
					p.setEstimatedEffort((int)(obj.get("remainingEffort")));
					p.setElapsedTime(new Date(((long)(obj.get("time"))*1000)));
					actualPoints.add(p);
				}
			}

//			while(cursor.hasNext()){
//				DBObject obj = cursor.next();
//				PointEntity p = new PointEntity();
//				p.setEstimatedEffort((int)(obj.get("remainingEffort")));
//				p.setElapsedTime(new Date(((long)(obj.get("time"))*1000)));
//				actualPoints.add(p);
//			}
		}catch(MongoException e){
			logger.warning("Mongo Exception");
		}

		return actualPoints;
	}

}
