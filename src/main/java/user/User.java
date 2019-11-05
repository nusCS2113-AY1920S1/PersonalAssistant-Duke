package user;

//@@ AmirAzhar
public class User {
    public String username;

    /**
     * Create new user account
     * @param username preferably name of the user
     */
    public User(String username) {
        this.username = username;
    }

    public String toWriteFile() {
        return username + "\n";
    }

    public String getUsername() {
        return username;
    }
}
