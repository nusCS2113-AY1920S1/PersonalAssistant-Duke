import command.AddUserCommand;
import org.junit.jupiter.api.Test;
import user.User;
import user.UserList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//@@ AmirAzhar
//AddUserTest2 is used to test if the UserList adds the new user as expected by checking its existence in the userList
public class AddUserTest2 {
    @Test
    void testAddUser() {
        String username = "Johnny Lim";
        User newUser = new User(username);
        UserList newUserList = new UserList();
        newUserList.add(newUser);
        assertTrue(newUserList.checkExistence(username));
    }
}
