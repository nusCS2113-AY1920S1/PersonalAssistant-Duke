import org.junit.jupiter.api.Test;
import user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@ AmirAzhar
//AddUserTest1 is used to test that when a new user is added, the appropriate text is written into the txt file
public class AddUserTest1 {
    @Test
    void testAddUser() {
        String username = "Johnny Lim";
        User newUser = new User(username);
        assertEquals("Johnny Lim\n", newUser.toWriteFile());
    }
}
