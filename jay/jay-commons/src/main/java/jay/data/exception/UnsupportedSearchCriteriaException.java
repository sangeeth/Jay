package jay.data.exception;

import jay.lang.UncheckedException;

/**
 * Thrown to indicate the given search criteria is not supported by {@link jay.data.SearchSupport}. 
 * @see jay.data.SearchSupport
 */
final public class UnsupportedSearchCriteriaException extends UncheckedException 
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs an instance of <code>UnsupportedSearchCriteriaException</code> with no detail message.
	 */
	public UnsupportedSearchCriteriaException() 
	{
		super(SearchErrorCode.UNSUPPORTED_SEARCH_CRITERIA);
	}
}
