package user;

public class Guest extends User{
    public Guest(String username) {
        super(username);
        this.userType = "G";
        this.email = "";
        this.password = "";
    }
}
