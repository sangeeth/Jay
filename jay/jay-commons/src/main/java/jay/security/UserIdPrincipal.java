package jay.security;

import java.io.Serializable;
import java.security.Principal;

public class UserIdPrincipal implements Principal, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String sdpUser;
    
    public UserIdPrincipal(String sdpUser) {
        super();
        this.sdpUser = sdpUser;
    }

    @Override
    public String getName() {
        return this.sdpUser;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserIdPrincipal)) {
            return false;
        }
        UserIdPrincipal that = (UserIdPrincipal)o;
        if (this.getName().equals(that.getName())) {
            return true;
        }
        return false;
    }
 
    public int hashCode() {
        return sdpUser.hashCode();
    }    
}
