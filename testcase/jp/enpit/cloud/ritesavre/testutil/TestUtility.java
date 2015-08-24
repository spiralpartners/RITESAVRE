package jp.enpit.cloud.ritesavre.testutil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.mongodb.DBObject;

public class TestUtility {
	
	/**
	 * String[]を一要素ごとに改行された一つのStringに変換する．改行コードは環境によって違うので，
	 * propertyを呼び出すようにしている
	 * @param expectedArray
	 * @return
	 */
	public static String joinByLineSeparator(String[] expectedArray){
		String expected="";
		String crlf = System.getProperty("line.separator");
		for(String s:expectedArray){
			expected = expected + s + crlf;
		}
		return expected;
	}
	
	/**
	 * 文字列にしたいBeanと文字列に含めたいそのオブジェクトのフィールド名を引数に与える
	 * @param o
	 * @param fieldString
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static String beanObjectToString(Object o, String... fieldString) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		List<String> objectString = new ArrayList<String>();
		for(String s:fieldString){
			objectString.add(s+":"+BeanUtils.getProperty(o, s));
		}
		return joinByLineSeparator(objectString.toArray(new String[0]));
	}
	
	public static String dbObjectToString(DBObject dbo, String... elementString){
		List<String> dbObjectString = new ArrayList<String>();		
		for(String s:elementString){
			dbObjectString.add(s+":"+ dbo.get(s).toString());
		}
		
		return joinByLineSeparator(dbObjectString.toArray(new String[0]));
	}
	
	
}
