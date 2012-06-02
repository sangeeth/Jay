package jay.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jay.data.annotation.SearchHandlerRef;
import jay.data.exception.UnsupportedSearchCriteriaException;
/**
 * This class helps to perform search with the given {@link SearchCriteria}.
 * 
 * It helps to maintain the {@link SearchCriteria} and {@link SearchHandler} associations.
 * 
 * @see SearchHandler
 * @see SearchCriteria
 * @see SearchResult
 * @see SearchContext
 */
public class SearchSupport {
    private Map<Class<? extends SearchCriteria>, SearchHandler> searchHandlerRegistry;
    /**
     * Constructs an instance of <code>SearchSupport</code>.
     */
    public SearchSupport() {
        super();
        this.searchHandlerRegistry = new HashMap<Class<? extends SearchCriteria>, SearchHandler>();
    }

    /**
     * Gets a set of supported search criteria types.
     * @return A set of supported search criteria types.
     */
    public Set<Class<? extends SearchCriteria>> getSupportedSearchCriteriaTypes() {
        return Collections.unmodifiableSet(searchHandlerRegistry.keySet());
    }

    /**
     * Gets the search handler registry; a map of {@link SearchCriteria} type and {@link SearchHandler} instance.
     * @return A map of {@link SearchCriteria} type and {@link SearchHandler} instance.
     */
    public Map<Class<? extends SearchCriteria>, SearchHandler> getSearchHandlerRegistry() {
        return searchHandlerRegistry;
    }

    /**
     * Sets the search handler registry; a map of {@link SearchCriteria} type and {@link SearchHandler} instance.
     * @param searchHandlerRegistry  A map of {@link SearchCriteria} type and {@link SearchHandler} instance.
     */
    public void setSearchHandlerRegistry(
            Map<Class<? extends SearchCriteria>, SearchHandler> searchHandlerRegistry) {
        this.searchHandlerRegistry = searchHandlerRegistry;
    }
    
    /**
     * Associates a {@link SearchCriteria} type to an instance of {@link SearchHandler}. 
     * 
     * @param criteriaType The search criteria type. 
     * @param handler The search hander instance which understands the given criteriaType.
     */
    public void registerSearchHandler(Class<? extends SearchCriteria> criteriaType, SearchHandler handler) {
        this.searchHandlerRegistry.put(criteriaType, handler);
    }
    
    /**
     * Checks whether or not the given criteriaType is supported (or known).
     * 
     * A {@link SearchCriteria} type is considered supported, if one the following conditions are satisfied.
     * <ol>
     *   <li>If the {@link #getSearchHandlerRegistry() searchHandlerRegistry} contains an association between
     * the given criteriaType and an instance of {@link SearchHandler}, then the criteriaType is considered
     * to be supported.
     *   </li>
     *   <li>If the {@link SearchCriteria} type is annotated using {@link SearchHandlerRef} annotation type.
     *   </li>
     * </ol>
     * 
     * @param criteriaType The type of the search criteria.
     * @return true, if the criteriaType is supported. false otherwise.
     * 
     * @see SearchHandler
     * @see SearchCriteria
     */
    public boolean isSupported(Class<? extends SearchCriteria> criteriaType) {
        boolean supported = this.searchHandlerRegistry.containsKey(criteriaType);
        if (!supported) {
            SearchHandlerRef searchHandlerRef = criteriaType.getAnnotation(SearchHandlerRef.class);
            supported = searchHandlerRef!=null;
        }
        return supported;
    }
    
    /**
     * Gets the {@link SearchHandler} instance for the given searchCriteria.
     * @param searchCriteria The search criteria for which the search handler is expected.
     * @return The instance of {@link SearchHandler} type which is associated to the give type of searchCriteria.
     */
    public SearchHandler getSearchHandler(SearchCriteria searchCriteria) {
        SearchHandler searchHandler  ;
        Class<? extends SearchCriteria> criteriaType = searchCriteria.getClass();
        
        searchHandler = this.searchHandlerRegistry.get(criteriaType);
        
        if (searchHandler==null) {
            SearchHandlerRef handlerRef = criteriaType.getAnnotation(SearchHandlerRef.class);
            if (handlerRef!=null) {
                Class<? extends SearchHandler> handlerType = handlerRef.value();
                
                try {
                    searchHandler = handlerType.newInstance();
                } catch(Exception e) {
                    // FIXME: P1 Handle this exception appropriately.
                    throw new RuntimeException(e);
                }
                
                this.searchHandlerRegistry.put(criteriaType, searchHandler);
            }
        }

        return searchHandler;
    }
    
    /**
     * Performs search using the {@link SearchHandler} instance associated to the given {@link SearchCriteria searchCriteria} type.
     *  
     * @param <E> The search result item type.
     * @param searchContext The search context.
     * @param itemType The expected search result item type.
     * @param searchCriteria The search criteria based on which the search need to be performed.
     * @return The search result.
     * @throws UnsupportedSearchCriteriaException If the given {@link SearchCriteria searchCriteria} is not {@link #isSupported(Class) supported}.
     * @throws IllegalArgumentException If the given search context is not compatible with the {@link SearchHandler} associated to the given {@link SearchCriteria searchCriteria}
     */
    public <E extends Object> SearchResult<E> search(SearchContext searchContext, 
                                             Class<E> itemType, 
                                             SearchCriteria searchCriteria) throws UnsupportedSearchCriteriaException, IllegalArgumentException {
        SearchResult<E> results  ;
        
        SearchHandler searchHandler = getSearchHandler(searchCriteria);
        
        if (searchHandler==null) {
            throw new UnsupportedSearchCriteriaException();
        }
        
        results = searchHandler.search(searchContext, searchCriteria);
        
        return results;
    }    
}
