package jp.enpit.cloud.ritesavre;

/**
 * UC: ログインする
 * @author fukuyasu
 *
 */
public class TEMException extends Exception {
	private static final long serialVersionUID = 4929153935825984769L;

	public TEMException() {
		super();
	}

	public TEMException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TEMException(String message, Throwable cause) {
		super(message, cause);
	}

	public TEMException(String message) {
		super(message);
	}

	public TEMException(Throwable cause) {
		super(cause);
	}

}
