public class Users {

    protected String email;

    protected String password;

    /**
     * Create your account with HallBooker
     * @param email your account email address
     * @param password your password
     */
    public void createAccount(String email, String password) {
        this.email = email;
        System.out.println("Please set your password.");
        this.password = password;
    }
}
