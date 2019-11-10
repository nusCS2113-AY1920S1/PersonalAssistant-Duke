import command.AddUserCommand;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import user.User;
import user.UserList;

import static org.junit.jupiter.api.Assertions.*;

//@@ author AmirAzhar
//AddUserTest2 is used to test if the UserList adds the new user as expected by checking its existence in the userList
public class AddUserTest1 {
    @Test
    void addUserTest1() {
        String username = "Johnny Lim";
        User newUser = new User(username);
        UserList newUserList = new UserList();
        assertFalse(newUserList.doesExist(username));
        newUserList.add(newUser);
        assertTrue(newUserList.doesExist(username));
        assertEquals("Johnny Lim\n", newUser.toWriteFile());
    }
}
