import org.junit.jupiter.api.Test;
import user.User;
import user.UserList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@ AmirAzhar
//RemoveUserTest1 is used to test if the removal of user works as expected by checking its existence in the userList
public class RemoveUserTest1 {
    @Test
    void testRemoveUser() {
        String username = "Johnny Lim";
        User newUser = new User(username);
        UserList newUserList = new UserList();
        newUserList.add(newUser);
        assertTrue(newUserList.doesExist(username));
        newUserList.removeUser(username);
        assertFalse(newUserList.doesExist(username));
    }
}
