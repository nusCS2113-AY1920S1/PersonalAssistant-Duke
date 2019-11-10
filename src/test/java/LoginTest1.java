import org.junit.jupiter.api.Test;
import user.User;
import user.UserList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@author AmirAzhar
//LoginTest1 is used to test if the variables currentUser and loginStatus are updated when login is performed
public class LoginTest1 {
    @Test
    void loginTest1() {
        String username = "Johnny Lim";
        UserList newUserList = new UserList();
        newUserList.addUser(username);
        newUserList.login(username);
        assertEquals("Johnny Lim", newUserList.getCurrentUser());
        assertTrue(newUserList.getLoginStatus());
    }
}
