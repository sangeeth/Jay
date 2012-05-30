package jay.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jay.data.SearchHandler;
/**
 * This annotation type helps in associating a {@link SearchHandler} to a {@link jay.data.SearchCriteria}.
 * 
 * For example, 
 * <pre>
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
 * </pre>
 * where SomeSearchHandler is as shown below
 * <pre>
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
 * </pre>
 * @see jay.data.SearchCriteria
 * @see jay.data.SearchHandler
 * @see jay.data.SearchSupport
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SearchHandlerRef {
    public Class<? extends SearchHandler> value();
}
