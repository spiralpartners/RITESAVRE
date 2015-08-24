package jp.enpit.cloud.ritesavre.model;

import jp.enpit.cloud.ritesavre.TEMException;

/**
 * UC: ログインする．
 *   AuthenticationFailureExceptionの親クラス．
 *   AuthenticationFailureExceptionはAccountDaoで使用される．
 */
public class TEMSystemException extends TEMException {

	private static final long serialVersionUID = 6388376710847740838L;

	public TEMSystemException (String msg) {
		super(msg);
	}
}
