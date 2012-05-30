package jay.data;

import jay.util.Context;
/**
 * This class represents the search context. 
 * 
 * A typical use of search context is to perform search and return paginated search results. 
 * The search context may either store the complete result or last fetched page details. Further,
 * it may also be useful in passing additional parameters to {@link SearchHandler}.
 * 
 * @see jay.data.hibernate.HibernateSearchContext
 */
public class SearchContext extends Context {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs the instance of <code>SearchContext</code>.
	 */
	public SearchContext() {
		super();
	}
}
