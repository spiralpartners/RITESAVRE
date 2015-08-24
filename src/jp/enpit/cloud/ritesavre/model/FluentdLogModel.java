package jp.enpit.cloud.ritesavre.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import jp.enpit.cloud.ritesavre.util.DBUtilsLogMongo;
import jp.enpit.cloud.ritesavre.view.UserAccessLog;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 
 * @author igaki
 *
 */
public class FluentdLogModel {
	
	private final String DB_APACHE_COLLECTION = "apache";
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

	public FluentdLogModel() {
		db = DBUtilsLogMongo.getInstance().getDb();
		coll = db.getCollection(DB_APACHE_COLLECTION);
	}

	/**
	 *  db.apache.find({user:/^[0-9]+$/},{user:1});
	 *
	 * @return
	 */
	public List<String> getAccessUsers(){
		DBObject query = new BasicDBObject();
		query.put("user", Pattern.compile("^[0-9]+$"));
		DBObject projection = new BasicDBObject();
		projection.put("user", 1);

		//DBCursor cur = coll.find(query,projection);
		DBCursor cur = coll.find(query,projection);
		HashSet<String> hash = new HashSet<String>();
		while(cur.hasNext()){
			hash.add((String)cur.next().get("user"));
			//System.out.println(cur.next().get("user"));
		}
		System.out.println(hash);
		List<String> userList = new ArrayList<String>(hash);
		return userList;
	}
	
	/**
	 * db.apache.find({$and:[{path:/html/},{path:{$not:/jenkins/}},
	 * {path:{$not:/svn/}},{path:{$not:/trac/}},{path:{$not:/RITESAVRE/}},{user:/[0-9]/}]})
	 */
	public UserAccessLog getEventSpiralAccessCount(String userId){
		DBObject query = new BasicDBObject();
		
		List<DBObject> filter = new ArrayList<DBObject>();
		DBObject filter1 = new BasicDBObject();
		DBObject filter2 = new BasicDBObject();
		DBObject filter3 = new BasicDBObject();
		DBObject filter4 = new BasicDBObject();
		DBObject filter5 = new BasicDBObject();
		DBObject filter6 = new BasicDBObject();
		DBObject filter7 = new BasicDBObject();
		DBObject filter8 = new BasicDBObject();
		DBObject filter9 = new BasicDBObject();
		DBObject user = new BasicDBObject();
		
		filter1.put("path", Pattern.compile("html"));
		filter.add(filter1);
		
		filter2.put("$not", Pattern.compile("jenkins"));
		filter3.put("path", filter2);
		filter.add(filter3);

		filter8.put("$not", Pattern.compile("svn"));
		filter9.put("path", filter8);
		filter.add(filter9);

		filter4.put("$not", Pattern.compile("trac"));
		filter5.put("path", filter4);
		filter.add(filter5);

		filter6.put("$not", Pattern.compile("RITESAVRE"));
		filter7.put("path", filter6);
		filter.add(filter7);

		user.put("user", userId);
		filter.add(user);
		
		query.put("$and", filter);

		//int access = coll.find(query).length();
		DBCursor userAccessCursor = coll.find(query);
		
		List<Date> accessDateList = new ArrayList<Date>();
		UserAccessLog al = new UserAccessLog();
		
		while(userAccessCursor.hasNext()){
			DBObject dbo = userAccessCursor.next();
			al.setUser((String)dbo.get("user"));
			Date acDate = (Date)dbo.get("time");
			accessDateList.add(acDate);
		}
		al.setAccessDateList(accessDateList);
		
		return al;
	}

}
