package jay.data;

abstract public class AbstractPagedResult<E> implements PagedResult<E> {
    private static final long serialVersionUID = 1L;

    private int startIndex;

    private int pageSize;

    private int totalSize;
    
    private boolean last;

    public AbstractPagedResult() {
        super();
        this.totalSize = TOTAL_SIZE_UNKNOWN;
    }

    @Override
    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
