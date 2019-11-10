import org.junit.jupiter.api.Test;
import user.UserList;

import static org.junit.jupiter.api.Assertions.*;

//@@author AmirAzhar
//LogoutTest1 is used to test if the variables currentUser and loginStatus are updated when logout is performed
public class LogoutTest1 {
    @Test
    void logoutTest1() {
        String username = "Johnny Lim";
        UserList newUserList = new UserList();
        newUserList.login(username);
        newUserList.logout();
        assertNull(newUserList.getCurrentUser());
        assertFalse(newUserList.getLoginStatus());
    }
}
