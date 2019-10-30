package user;

public class UnusedGuest extends UnusedUser {

    /**
     * Dummy class for not logged in user.
     * @param username a random name input
     */
    public UnusedGuest(String username) {
        super(username);
        this.userType = "G";
        this.email = "";
        this.password = "";
    }
}
