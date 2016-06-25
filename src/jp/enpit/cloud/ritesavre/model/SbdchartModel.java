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
	 * Logger、DB、DBCollectionフィールドに各オブジェクトを設定する．
	 */

	public SbdchartModel() {
		logger = Logger.getLogger(getClass().getName());
		db = DBUtils.getInstance().getDb();
		coll = db.getCollection(DB_SBDCHART_COLLECTION);

	}

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

		try{
			DBCursor cursor = coll.find(keys);
			while(cursor.hasNext()){
				DBObject obj = cursor.next();
				PointEntity p = new PointEntity();
				p.setEstimatedEffort((int)(obj.get("remainingEffort")));
				p.setElapsedTime(new Date(((long)(obj.get("time"))*1000)));
				actualPoints.add(p);
			}
		}catch(MongoException e){
			logger.warning("Mongo Exception");
		}

		return actualPoints;
	}

}
