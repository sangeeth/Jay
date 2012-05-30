package jay.util;

import java.util.Date;

public interface IAuditable
{
    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedOn();

    void setCreatedOn(Date createdDate);

    String getUpdatedBy();

    void setUpdatedBy(String lastUpdateBy);

    Date getUpdatedOn();

    void setUpdatedOn(Date lastUpdatedDate);
}
