import org.junit.jupiter.api.Test;
import user.UserList;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTest1 {
    @Test
    void testLogout() {
        String username = "Johnny Lim";
        UserList newUserList = new UserList();
        newUserList.login(username);
        newUserList.logout();
        assertNull(newUserList.getCurrentUser());
        assertFalse(newUserList.getLoginStatus());
    }
}
