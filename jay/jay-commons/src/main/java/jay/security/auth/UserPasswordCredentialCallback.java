package jay.security.auth;

import javax.security.auth.callback.Callback;

public class UserPasswordCredentialCallback implements Callback {
    private UserPasswordCredential passwordCredential;

    public UserPasswordCredentialCallback() {
        super();
        // TODO Auto-generated constructor stub
    }

    public UserPasswordCredential getPasswordCredential() {
        return passwordCredential;
    }

    public void setPasswordCredential(UserPasswordCredential passwordCredential) {
        this.passwordCredential = passwordCredential;
    }

    
}
