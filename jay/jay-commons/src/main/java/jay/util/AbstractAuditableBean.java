package jay.util;

import java.io.Serializable;
import java.util.Date;

abstract public class AbstractAuditableBean implements Serializable, IAuditable {
	private static final long serialVersionUID = 1L;

	private String createdBy;

	private Date createdOn;

	private String updatedBy;

	private Date updatedOn;

	public AbstractAuditableBean() {
		super();
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
}
