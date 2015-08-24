package jp.enpit.cloud.ritesavre.testutil;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.enpit.cloud.ritesavre.util.DBUtils;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * DBの初期化用Utilクラス
 * 基本的には public static アクセス可能な以下2つのメソッドを利用すること
 * 
 * void init()
 *   - 復元用DBの初期化
 *   
 * void restore() 
 *   - 復元用DBからのアプリDBの復元．高パフォーマンス．
 *   - テストの@Beforeではこちらを利用
 *   
 * @author さちお
 *
 */
public class InitialDataUtils {

	private Mongo m;
	private DB temDb;
	private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public InitialDataUtils() {
		try {
			m = new Mongo("localhost", 27017);
			//temDb = m.getDB(BACKUPDB_NAME);
			temDb = DBUtils.getInstance().getDb();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 復元用DBの完全初期化
	 * 各テストケースの @Before で呼び出すこと
	 */
	public static void init() {
		InitialDataUtils idu = new InitialDataUtils();
		idu.initAccount();
	}

	private void initAccount(){
		DBCollection coll = temDb.getCollection("account");
		coll.drop();
		registerInitAccount("user1","pass1","user");
		registerInitAccount("user2","pass2","user");
		registerInitAccount("user3","pass3","user");
		registerInitAccount("user4","pass4","user");
		registerInitAccount("user5","pass5","user");
		registerInitAccount("user0","pass0","user");
		registerInitAccount("igaki","spiral","promoter");
		registerInitAccount("fukuyasu","spiral","promoter");
		registerInitAccount("matsu","spiral","promoter");
		registerInitAccount("tamada","spiral","promoter");
		registerInitAccount("admin","admin","administrator");
	}




	private void registerInitAccount(String userid, String pass, String role){
		DBCollection coll = temDb.getCollection("account");
		coll.insert(new BasicDBObject()
		.append("userId", userid)
		.append("pass", pass)
		.append("role", role)
		.append("sessionId", "")
				);
	}

	private Date toDate(String str) {
		try {
			return SDF.parse(str);
		} catch (ParseException e) {
			System.err.println("InitialDataUtils: パースエラー");
		}
		return null;
	}
}
