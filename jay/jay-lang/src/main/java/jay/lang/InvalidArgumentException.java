package jay.lang;

import java.util.List;

/**
 * This exception class can be used when an argument given to a method or an
 * operation is not valid.
 * 
 * It also helps to carry one or more validation errors.
 */
final public class InvalidArgumentException extends UncheckedException {
	private static final long serialVersionUID = 1L;

	/**
	 * The list of validation errors.
	 */
	private final List<ValidationError> validationErrors;

	/**
	 * Constructs a new instance of InvalidArgumentException with error code as
	 * {@link CommonErrorCode#INVALID_ARGUMENT}
	 */
	public InvalidArgumentException() {
		this(CommonFaultCode.INVALID_ARGUMENT);
	}

	/**
	 * Constructs a new instance of InvalidArgumentException with the given
	 * faultCode, cause and message arguments.
	 * 
	 * @param faultCode
	 *            The error code this exception referring to.
	 * @param cause
	 *            The cause for this exception.
	 * @param messageArgs
	 *            The arguments required to construct the localized error
	 *            message.
	 */
	public InvalidArgumentException(IFaultCode faultCode, Exception cause,
			Object... messageArgs) {
		super(faultCode, cause, messageArgs);
		this.validationErrors = null;
	}

	/**
	 * Constructs a new instance of InvalidArgumentException with the given
	 * faultCode and message arguments.
	 * 
	 * @param faultCode
	 *            The error code this exception is referring to.
	 * @param messageArgs
	 *            The arguments required to construct the localized error
	 *            message.
	 */
	public InvalidArgumentException(IFaultCode faultCode, Object... messageArgs) {
		this(faultCode, null, messageArgs);
	}

	/**
	 * Constructs a new instance of InvalidArgumentException with the given list
	 * of validation errors. The error code will be assigned to
	 * {@link CommonErrorCode#INVALID_ARGUMENT}.
	 * 
	 * @param validationErrors
	 *            The list of validation errors.
	 */
	public InvalidArgumentException(List<ValidationError> validationErrors) {
		this(validationErrors, CommonFaultCode.INVALID_ARGUMENT);
	}

	/**
	 * Constructs a new instance of InvalidArgumentException with the given list
	 * of validation errors, faultCode and message arguments.
	 * 
	 * @param validationErrors
	 *            The list of validation errors.
	 * @param faultCode
	 *            The error code this exception is referring to.
	 * @param messageArgs
	 *            The arguments required to construct the localized error
	 *            message.
	 */
	public InvalidArgumentException(List<ValidationError> validationErrors,
			IFaultCode faultCode, Object... messageArgs) {
		this(validationErrors, faultCode, null, messageArgs);
	}

	/**
	 * Constructs a new instance of InvalidArgumentException with the given list
	 * of validation errors, faultCode, cause and message arguments.
	 * 
	 * @param validationErrors
	 *            The list of validation errors.
	 * @param faultCode
	 *            The error code this exception is referring to.
	 * @param cause
	 *            The cause for this exception.
	 * @param messageArgs
	 *            The arguments required to construct the localized error
	 *            message.
	 */
	public InvalidArgumentException(List<ValidationError> validationErrors,
			IFaultCode faultCode, Exception cause, Object... messageArgs) {
		super(faultCode, cause, messageArgs);
		this.validationErrors = validationErrors;
	}

	/**
	 * Gets the list of validation faults caused this exception.
	 * 
	 * @return The list of validation faults caused this exception.
	 */
	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

}
