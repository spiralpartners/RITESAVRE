package jp.enpit.cloud.ritesavre;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import jp.enpit.cloud.ritesavre.SessionModel;
import jp.enpit.cloud.ritesavre.testutil.InitialDataUtils;
import jp.enpit.cloud.ritesavre.util.DBUtils;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class SessionModelTest {

	@Before
	//accountコレクションの中身をテストメソッド毎に初期化
	public void initialize() throws Exception{
		InitialDataUtils.init();
	}



	@Test
	public void TestRegisterSessionId01() throws Exception{
		try{
			//SessionIDの登録
			String loginuser = "user1";
			SessionModel sdao = new SessionModel();
			sdao.registerSessionId(loginuser);

			//登録されているかDBで確認
			DB db = DBUtils.getInstance().getDb();
			DBCollection coll = db.getCollection("account");
			DBObject query = new BasicDBObject();
			query.put("sessionId", "THIS_IS_A_TEST_SESSION_ID");
			DBObject result = coll.findOne(query);
			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void TestDeleteSessionId() throws Exception{
		try{
			//まずは登録
			DB db = DBUtils.getInstance().getDb();
			DBCollection coll = db.getCollection("account");
			// DWRの発行するSessionIdの取得（のエミュレーション）
			String sessionId = "THIS_IS_A_TEST_SESSION_ID";
			String loginuser = "user1";
			// 登録
			DBObject query = new BasicDBObject();
			query.put("userId", loginuser);
			DBObject update = new BasicDBObject();
			update.put("$set", new BasicDBObject("sessionId", sessionId));
			coll.update(query, update);
			
			//セッションIDの削除
			SessionModel sdao = new SessionModel();
			sdao.deleteSessionId();
			
			//消えているかDBで確認
			DBObject query2 = new BasicDBObject();
			query2.put("sessionId", sessionId);
			DBObject result = coll.findOne(query2);
			
			assertNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();

		}

	}
}
