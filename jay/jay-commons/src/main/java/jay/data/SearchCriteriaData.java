package jay.data;

import java.io.Serializable;
import java.util.List;

import jay.util.NamedValue;

public class SearchCriteriaData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private PageCriteria pageCriteria;

	private List<NamedValue> properties;

	public SearchCriteriaData() {
		super();
	}

	public SearchCriteriaData(String id, PageCriteria pageCriteria,
			List<NamedValue> properties) {
		super();
		this.id = id;
		this.pageCriteria = pageCriteria;
		this.properties = properties;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PageCriteria getPageCriteria() {
		return pageCriteria;
	}

	public void setPageCriteria(PageCriteria pageCriteria) {
		this.pageCriteria = pageCriteria;
	}

	public List<NamedValue> getProperties() {
		return properties;
	}

	public void setProperties(List<NamedValue> properties) {
		this.properties = properties;
	}
}
