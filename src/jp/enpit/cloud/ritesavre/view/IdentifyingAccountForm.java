package jp.enpit.cloud.ritesavre.view;

import javax.validation.constraints.Pattern;

import jp.enpit.cloud.ritesavre.util.DBUtils;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * UC: ログインする
 * TEMからコピーする際にコンストラクタを削除
 * @author fukuyasu
 *
 */
public class IdentifyingAccountForm extends AbstractForm {

	@Length(min=4, max=12, message="ユーザIDには4から12文字の英数字のみが利用できます")
	@Pattern(regexp="^[a-zA-Z0-9]*$", message="ユーザIDには4から12文字の英数字のみが利用できます")
	@NotEmpty(message="ユーザIDには4から12文字の英数字のみが利用できます")
	private String userId;
	
	@Length(min=4, max=12, message="パスワードには4から12文字の英数字のみが利用できます")
	@Pattern(regexp="^[a-zA-Z0-9]*$", message="パスワードには4から12文字の英数字のみが利用できます")
	@NotEmpty(message="ユーザIDには4から12文字の英数字のみが利用できます")
	private String pass;
	
	public String getPass() {
		return pass;
	}
	public String getUserId() {
		return userId;
	}
	public void setPass(String pass) {
		this.pass = DBUtils.sanitize(pass);
	}
	public void setUserId(String userId) {
		this.userId = DBUtils.sanitize(userId);
	}

}
