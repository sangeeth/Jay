package jay.data;

import java.io.Serializable;

/**
 * This interface is a marker interface representing a search criteria.
 * 
 * A search criteria is useful only with a {@link SearchHandler}. Each search criteria type should be associated to a
 * {@link SearchHandler} using {@link SearchSupport}. The different ways of associating a search criteria to {@link SearchHandler} are shown below.
 * <ol>
 *   <li>Using {@link jay.data.annotation.SearchHandlerRef} as shown below
 *       <pre>
 * &#64;SearchHandlerRef(SomeSearchHandler.class)
 * public class SomeSearchCriteria implements SearchCriteria {
 *     private String pattern;
 *     
 *     public SomeSearchCriteria(String pattern) {
 *         this.pattern = pattern;
 *     }
 *     public String getPattern() {
 *         return pattern;
 *     }
 * }
 *       </pre>
 *       where SomeSearchHandler is as shown below
 *       <pre>
 * public class SomeSearchHandler implements SearchHandler&lt;SearchContext,SomeSearchCriteria,SomeBean> {
 *    public SearchResult&lt;SomeBean> search(SearchContext context, SomeSearchCriteria criteria) {
 *        SearchResult&lt;SomeBean> result = null;
 *        
 *        List&lt;SomeBean> items = new ArrayList&lt;SomeBean>();
 *        
 *        // Logic to search and populate 'items' list.
 *        // ...
 *        // ...
 *        
 *        result = new BasicSearchResult&lt;SomeBean>(items);
 *        
 *        return result;
 *    }
 * }
 *       </pre>
 *   </li>
 *   <li>Using {@link SearchSupport} as shown below
 *       <pre>
 * SearchSupport searchSupport = new SearchSupport();
 * searchSupport.registerSearchHandler(SomeSearchCriteria.class, new SomeSearchHandler());
 *       </pre>
 *       In this case, the class definition of SomeSearchCriteria need not include the SearchHandlerRef annotation. That is,
 *       the class definition can be as shown below
 *       <pre>
 * public class SomeSearchCriteria implements SearchCriteria {
 *     private String pattern;
 *     
 *     public SomeSearchCriteria(String pattern) {
 *         this.pattern = pattern;
 *     }
 *     public String getPattern() {
 *         return pattern;
 *     }
 * }
 *       </pre>
 *       This mode helps in applications or scenarios where-in the {@link SearchCriteria} instance can be allowed to be
 *       created remotely and {@link SearchHandler} instance creation cannot be allowed.
 *   </li>
 * </ol>
 * 
 * @see SearchHandler
 * @see SearchResult
 * @see SearchSupport
 * @see SearchContext
 * @see jay.data.annotation.SearchHandlerRef 
 */
public interface SearchCriteria extends Serializable {
}
