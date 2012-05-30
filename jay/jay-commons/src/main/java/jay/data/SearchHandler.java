package jay.data;
/**
 * This interface represents the search handler, which performs the search operation using the 
 * given {@link SearchCriteria} and returns the {@link SearchResult}.  
 * 
 * The search handler is useful when it is associated to a {@link SearchCriteria}. A search handler can be
 * associated to a {@link SearchCriteria} in one of the following methods
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
 *       In this case, the class definition of SomeSearchCriteria need not include the SearchHandlerRef annotation. That is
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
 * @param <C> The search context type.
 * @param <T> The search criteria type
 * @param <E> The search result item type.
 * 
 * @see SearchCriteria
 * @see SearchResult
 * @see SearchSupport
 * @see SearchContext
 * @see jay.data.annotation.SearchHandlerRef 
 */
public interface SearchHandler<C extends SearchContext, T extends SearchCriteria, E> {
    public SearchResult<E> search(C context, T criteria);
}