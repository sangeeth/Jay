package jay.data;

public class AbstractSearchCriteria implements SearchCriteria {
	private static final long serialVersionUID = 1L;

	private PageCriteria pageCriteria;

	public AbstractSearchCriteria() {
		super();
	}

	public PageCriteria getPageCriteria() {
		return pageCriteria;
	}

	public void setPageCriteria(PageCriteria pageCriteria) {
		this.pageCriteria = pageCriteria;
	}
}
