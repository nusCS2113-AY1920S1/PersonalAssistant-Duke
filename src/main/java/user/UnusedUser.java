package user;

//@@author AmirAzhar
public class UnusedUser {
    protected String email;
    protected String password;
    protected String username;
    protected String userType;
    protected boolean loginStatus;

    /**
     * User account.
     * @param email NUS email (xxx@u.nus.edu)
     * @param password alphanumerical with no special characters
     * @param username preferably name of the user
     */
    public UnusedUser(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public UnusedUser(String username, String userType) {

    }

    public UnusedUser(String username) {

    }

    public String toWriteFile() {
        return email + " | " + password + " | " + username;
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus() {
        loginStatus = true;
    }


    public String getUsername() {
        return username;
    }
}
