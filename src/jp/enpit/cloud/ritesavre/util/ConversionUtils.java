package jp.enpit.cloud.ritesavre.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

public class ConversionUtils {


	/**
	 * 属性名が一致する属性をfromからdestに変換する．
	 * destが持たないfromの属性値は無視される．
	 * 
	 * @param dest
	 * @param from
	 * @return destを返す．変換に失敗すると，nullを返す．
	 */
	@SuppressWarnings("unchecked")
	public static void convert(Object dest, Object from){
//		System.out.println("-----------"+dest.getClass() + " | " + from.getClass());/////////
		
		// 指定要素のインタフェースを全て調べる
		for (Class<?> c : dest.getClass().getInterfaces()) {
			// List を発見した場合
			if (c == java.util.List.class) {
				throw new RuntimeException("ConversionUtils.convert() はList<T>型オブジェクトの変換に対応していません．");
			}
		}
		
		// fromの持つ全ての属性をそれぞれ探索
		Field[] fields = from.getClass().getDeclaredFields();
		for (Field field : fields) {
//			System.out.println(">"+ field.getName());/////////////
			// 属性値を取得
			Object property;
			try {
				property = PropertyUtils.getProperty(from, field.getName());
				// destでも取得してみる（例外起きるかどうかチェック）．
				PropertyUtils.getProperty(dest, field.getName());
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
				// dest側に存在しないfromの属性値を発見．無視して次の属性値へ
				continue;
			}

			if (property != null) {
				// 継承先を全て探索
				for (Class<?> c : property.getClass().getInterfaces()) {

					// List を発見した場合
					if (c == java.util.List.class) { // TODO Map等の他のコレクションはどうするか

						try {
							// dest側のgenericsのClassオブジェクトを取得
							ParameterizedType ptype = (ParameterizedType)dest.getClass().getDeclaredField(field.getName()).getGenericType();
							Class<?> cls = (Class<?>)ptype.getActualTypeArguments()[0];

							List<Object> list = new ArrayList<Object>();
							// 全てのリストを変換
							for (Object obj : (List<Object>)property) {
								Object o = cls.newInstance();

								convert(o, obj);
								list.add(o);
							}
							property = list;

							// 他の継承クラスは無視
							break;
						} catch (IllegalAccessException | InstantiationException | NoSuchFieldException | SecurityException e) {
							// TODO 例外を考える
							// Listの中身が不可視トラクタ，Listのジェネリクスが未指定などの場合
							// 無視する．それでよさそう
							continue;
						}
					}
				}
			}
			try {
				// メイン．属性を貼る
				PropertyUtils.setProperty(dest, field.getName(), property);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				// TODO これもわからん．たぶん上側で捕まるので起きない
				continue;
			}
		}
	}


}
