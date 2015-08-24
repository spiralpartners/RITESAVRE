package jp.enpit.cloud.ritesavre.model;

import java.util.logging.Logger;

import jp.enpit.cloud.ritesavre.util.DBUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MilestoneModel {

	private final String DB_MILESTONE_COLLECTION = "milestone";
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

	public MilestoneModel() {
		logger = Logger.getLogger(getClass().getName());
		db = DBUtils.getInstance().getDb();
		coll = db.getCollection(DB_MILESTONE_COLLECTION);
	}

	/**
	 * @deprecated
	 * @param milestone
	 * @param milestoneStart
	 */
	public void registerMilestoneDateTime(String milestone, long milestoneStart){
		DBObject query = new BasicDBObject();
		query.put("milestone", milestone);

		DBObject updateCondition = new BasicDBObject();
		updateCondition.put("milestoneStart", milestoneStart);

		DBObject update = new BasicDBObject();

		update.put("$set", updateCondition);

		coll.update(query, update, true, true);


	}

	/**
	 * @param milestone マイルストーン名．例：preSprint
	 * @param project プロジェクト名(DB名) 例：trac_EventSpiral
	 * @param milestoneStart マイルストーン開始時刻 例：ミリ秒単位のUNIXTIME
	 */
	public void registerMilestone(String milestone, String project, long milestoneStart){
		DBObject query = new BasicDBObject();
		query.put("milestone", milestone);

		DBObject updateContents = new BasicDBObject();
		updateContents.put("project", project);
		updateContents.put("milestoneStart", milestoneStart);
		

		DBObject update = new BasicDBObject();

		update.put("$set", updateContents);

		coll.update(query, update, true, true);


	}

	public Milestone getMilestone(String milestone) throws MilestoneNotDefinedException{
		DBObject query = new BasicDBObject();
		query.put("milestone", milestone);


		DBObject result = coll.findOne(query);

		if(result == null){
			throw new MilestoneNotDefinedException("MileStone is not defined");
		}

		Milestone ms = new Milestone();
		DBUtils.attachProperties(ms, result);

		return ms;

	}

}
