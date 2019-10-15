package user;

public class User {
    protected String email;
    protected String password;
    protected String username;
    protected String userType;
    protected boolean loginStatus;

    /**
     *
     * @param email NUS email (xxx@u.nus.edu)
     * @param password alphanumerical with no special characters
     * @param username preferably name of the user
     * @param userType A (Admin), R (Resident) or C (Club Head)
     */
    public User(String email, String password, String username, String userType){
        this.email = email;
        this.password = password;
        this.username = username;
        this.userType = userType;
    }

    public User(String username, String userType) {

    }

    public User (String username) {

    }

    public String toWriteFile() {
        return email + " | " + password + " | " + username + " | " + userType;
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
