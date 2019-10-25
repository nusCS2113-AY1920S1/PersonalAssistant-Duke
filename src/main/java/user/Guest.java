package user;

public class Guest extends User {

    /**
     * Dummy class for not logged in user.
     * @param username
     */
    public Guest(String username) {
        super(username);
        this.userType = "G";
        this.email = "";
        this.password = "";
    }
}
