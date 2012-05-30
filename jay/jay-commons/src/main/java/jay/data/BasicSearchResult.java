package jay.data;

import java.util.List;
/**
 * This class represents a basic implementation of {@link SearchResult} interface.
 * 
 * @param <E> The search result item type.
 */
public class BasicSearchResult<E> extends AbstractSearchResult<E> {
    private static final long serialVersionUID = 1L;

    private List<E> items;

    /**
	 * Constructs an instance of <code>BasicSearchResult</code> with the given list of search result items.
	 * @param items The search result items.
	 */
	public BasicSearchResult(List<E> items) {
		super();
		this.items = items;
	}

	@Override
	public List<E> getItems() {
		return this.items;
	}
}