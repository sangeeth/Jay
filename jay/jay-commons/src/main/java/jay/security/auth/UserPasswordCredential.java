package jay.security.auth;

import java.util.Arrays;

public class UserPasswordCredential implements ICredential {
    private String user;
    private char [] password;
    public UserPasswordCredential(String user, char[] password) {
        super();
        this.user = user;
        this.password = password;
    }
    public String getUser() {
        return user;
    }
    public char[] getPassword() {
        return password;
    }
    public void purge() {
        this.user = null;
        Arrays.fill(password, '\0');
        this.password = null;
    }
}
