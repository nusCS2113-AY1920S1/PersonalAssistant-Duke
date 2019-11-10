import org.junit.jupiter.api.Test;
import user.User;
import user.UserList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@ AmirAzhar
//LoginTest1 is used to test if the variables currentUser and loginStatus are updated when login is performed
public class LoginTest1 {
    @Test
    void testLogin() {
        String username = "Johnny Lim";
        UserList newUserList = new UserList();
        newUserList.login(username);
        assertEquals("Johnny Lim", newUserList.getCurrentUser());
        assertTrue(newUserList.getLoginStatus());
    }
}
