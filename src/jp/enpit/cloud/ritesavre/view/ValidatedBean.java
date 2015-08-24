package jp.enpit.cloud.ritesavre.view;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatedBean {

	public boolean validate() throws TEMValidationException{
		String className = getClass().getName();
		int index = className.lastIndexOf(".");
		className = className.substring(index + 1);
		Logger logger = Logger.getLogger(getClass().getName());
		logger.info(className + ".validate");

		try{
			return ValidatedBean.<ValidatedBean>validate(this);
		} catch(TEMValidationException e){
			logger.warning(className + ".validate: " + e.getMessage());
			throw e;
		}
	}

	private static <T extends ValidatedBean> boolean validate(T bean) throws TEMValidationException {
		// Hibernate Validator (HB) のvalidatorインスタンス生成
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		// validation
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean);

		// 制約違反発見
		if (constraintViolations.size() > 0) {
			Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();

			// 各制約違反に対する処理
			while (iterator.hasNext()) {
				// 例外をメッセージ付きで投げる．
				throw new TEMValidationException(iterator.next().getMessage());
			}
		}
		return true;
	}

}
