package jay.data;

import java.io.Serializable;

public class SortField implements Serializable {
    private static final long serialVersionUID = 1L;

    public static enum SortOrder {
        ASC, DESC
    }

    private SortOrder sortOrder;

    private String fieldName;

    public SortField() {
        this.sortOrder = SortOrder.ASC;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
