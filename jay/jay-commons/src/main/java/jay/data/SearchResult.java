package jay.data;

import java.io.Serializable;
import java.util.List;
/**
 * This interface represents the search result.
 *
 * @param <E> The search result item type.
 */
public interface SearchResult<E> extends Serializable{
    public int TOTAL_SIZE_UNKNOWN = -1;

    public int getStartIndex();

    public int getPageSize();

    public int getTotalSize();
    
	/**
	 * Returns a list of search result items.
	 * 
	 * If there are no items, then the returned list will be non-null and empty. This method at any circumstance
	 * will not (or should not) return null.
	 *  
	 * @return A list of search result items.
	 */
	public List<E> getItems();
}
