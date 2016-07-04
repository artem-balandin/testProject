package utility;

/**
 * Created by sheriff on 6/13/16.
 */
public class ApiCredentials {

    private String userName;
    private String password;

    public ApiCredentials(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
