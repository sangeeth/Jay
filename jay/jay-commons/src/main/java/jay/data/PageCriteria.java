package jay.data;

import java.io.Serializable;
import java.util.List;

public final class PageCriteria implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SIZE = 20;

	private int startIndex;

	private int size;

	private List<SortField> sortFields;

	private boolean totalSizeRequired;

	public PageCriteria() {
		this.size = DEFAULT_SIZE;
	}

	public PageCriteria(int startIndex, int size) {
		this.startIndex = startIndex;
		this.size = size <= 0 ? DEFAULT_SIZE : size;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size <= 0 ? DEFAULT_SIZE : size;
	}

	public List<SortField> getSortFields() {
		return sortFields;
	}

	public void setSortFields(List<SortField> sortFields) {
		this.sortFields = sortFields;
	}

	public boolean isTotalSizeRequired() {
		return totalSizeRequired;
	}

	public void setTotalSizeRequired(boolean totalSizeRequired) {
		this.totalSizeRequired = totalSizeRequired;
	}
}