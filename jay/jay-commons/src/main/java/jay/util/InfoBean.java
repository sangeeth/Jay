package jay.util;

abstract public class InfoBean extends AbstractAuditableBean {
	private static final long serialVersionUID = 1L;

	private Long id;

	public InfoBean() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
