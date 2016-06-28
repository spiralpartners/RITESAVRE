package jp.enpit.cloud.ritesavre.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


/**
 * Mongodbを利用するためのシングルトンUtilityクラス
 * @author shinsuke-m
 *
 */
public class DBUtils {
	private static Mongo m;
	private static DB db;
	private static final String dbName = "ritesavre";

	// for singleton
	private static DBUtils singleton = new DBUtils();

	public static DBUtils getInstance() {
		return singleton;
	}

	// invisible singleton constructor
	private DBUtils() {
		try {
			//m = new Mongo("localhost", 27017);
			m = new Mongo("133.1.236.176", 27017);
			db = m.getDB(dbName);
		} catch (UnknownHostException | MongoException e) {
			// TODO 自動生成された catch ブロック
			// どうすんだろこれ
			e.printStackTrace();
		}
	}

	/**
	 * シングルトンDBコネクションの取得
	 * @return DBコネクションオブジェクト
	 */
	public DB getDb() {
		return db;
	}

	/**
	 * HTMLタグ周りをサニタイジング
	 * @param str
	 * @return
	 */
	public static String sanitize(String str) {
		if (str == null) {
			return "";
		}
		str = str.replaceAll("&" , "&amp;" );
		str = str.replaceAll("<" , "&lt;"  );
		str = str.replaceAll(">" , "&gt;"  );
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("'" , "&#39;" );
		return str;
	}
	/**
	 * DBObjectのプロパティをObjectに移し替えるユーティリティメソッド．
	 * @param dest 変換先のObject
	 * @param from 変換元のDBObject
	 */
	@SuppressWarnings("unchecked")
	public static void attachProperties(Object dest, DBObject from){
		//		System.out.println("========================="+dest.getClass() + " | " + from.getClass());
		Field[] fields = dest.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 変換対象のオブジェクトプロパティを取得
			//Object property = PropertyUtils.getProperty(dest, field.getName());
			Object property = from.get(field.getName());

			// ネストされたオブジェクトを探す
			if (property != null) {
				//				System.out.println("  " + property.getClass() + " " + field.getName());

				// 継承先を全て探索
				for (Class<?> c : property.getClass().getInterfaces()) {
					//					System.out.println("     " + c);
					// List を発見した場合  TODO:他のコレクションクラス
					if (c == com.mongodb.DBObject.class || c == java.util.List.class) {
						try {
							// genericsのClassオブジェクトを取得
							ParameterizedType ptype = (ParameterizedType)dest.getClass().getDeclaredField(field.getName()).getGenericType();
							Class<?> cls = (Class<?>)ptype.getActualTypeArguments()[0];

							List<Object> list = new ArrayList<Object>();

							// 全てのリストを変換
							for (DBObject dbo : (List<DBObject>)from.get(field.getName())) {
								Object o = cls.newInstance();
								attachProperties(o, dbo);
								list.add(o);
							}

							property = list;

							// 他の継承クラスは無視
							break;
						} catch (IllegalAccessException | InstantiationException | SecurityException e) {
							continue;
						} catch(NoSuchFieldException e){
						    throw new IllegalArgumentException();
						}
					}
				}
			}
			if(!field.getName().startsWith("_") && !field.getName().startsWith("$")){
			    try {
			        PropertyUtils.setProperty(dest, field.getName(), property);
			    } catch (IllegalAccessException | InvocationTargetException e) {
			        continue; //TODO
			    } catch(NoSuchMethodException e){
			        throw new IllegalArgumentException(e);
			    }
			}
		}

	}


	/**
	 * ObjectのプロパティをDBObjectに入れ替えるユーティリティメソッド．
	 *
	 * @param dest 変換先のDBObject
	 * @param from 変換元のObject
	 */
	@SuppressWarnings("unchecked")
	public static void convertToDBObject(DBObject dest, Object from){
		try {
			// from側の属性から探索する
			Field[] fields = from.getClass().getDeclaredFields();
			for (Field field : fields) {
				//TODO カバレッジツールが"$jaccoData"というフィールドを
				//カバレッジ計測のためにクラスに埋め込むので，回避するために実装を修正した．挙動的に問題がないか要確認
				if(field.getName()=="$jacocoData") continue;
				// destの同名オブジェクトプロパティを取得
				Object property = PropertyUtils.getProperty(from, field.getName());

				// ネストされたオブジェクトを探す
				if (property != null) {

					// 継承先を全て探索
					for (Class<?> c : property.getClass().getInterfaces()) {

						// List を発見した場合
						// TODO Map等の他のコレクションはどうするか
						if (c == java.util.List.class) {
							List<DBObject> list = new ArrayList<DBObject>();
							for (Object p : (List<Object>)property) {

								// 再帰呼び出しで変換
								DBObject o = new BasicDBObject();
								convertToDBObject(o, p);
								list.add(o);
							}
							property = list;

							// 他の継承クラスは無視
							break;
						}
					}
				}
				dest.put(field.getName(), property);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			Logger logger = Logger.getLogger(DBUtils.class.getName());
			logger.warning("DBUtils.convertToDBObject: " + e.getMessage());
			//System.out.println("DBUtils.convertToDBObject: " + e.getMessage());

			// RuntimeExceptionをスロー
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Add ignored properties
	 * @param dest
	 * @param from
	 * @param ignoreProperties
	 */
	public static void attachProperties(Object dest, DBObject from, String[] ignoreProperties){
		try {
			for(String name: from.keySet()){
				// Mongo固有フィールドである _id は差し替えない
				if (name.startsWith("_") || name.startsWith("$")) continue;
				for(String ignore:ignoreProperties){
					if (name.equals(ignore)) continue;
				}
				PropertyUtils.setProperty(dest, name, from.get(name));
			}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			Logger logger = Logger.getLogger(DBUtils.class.getName());
			logger.warning("DBUtils.attachProperties: " + e.getMessage());

			// RuntimeExceptionをスロー
			throw new IllegalArgumentException();
		}
	}

}
