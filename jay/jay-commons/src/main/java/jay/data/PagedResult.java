package jay.data;

import java.io.Serializable;
import java.util.List;
/**
 * This interface represents a paged result.
 *
 * @param <E> The result item type.
 */
public interface PagedResult<E> extends Serializable{
    public int TOTAL_SIZE_UNKNOWN = -1;

    public int getStartIndex();

    public int getPageSize();

    public int getTotalSize();
    
    public List<E> getItems();
}
