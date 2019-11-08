package diyeats.logic.command;

import diyeats.logic.commands.UserSetup;
import diyeats.model.user.Gender;
import diyeats.model.user.User;
import diyeats.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserSetupTest {
    private Storage storage = new Storage();
    private User user = new User();
    private UserSetup setup = new UserSetup(user);

    @Test
    void userSetupTest() {
        setup.initialise("/name Foo Chi Hen /age 22 /gender Male");
        assertEquals(setup.getIsDone(), false);
        setup.initialise("/activity asdf");
        assertEquals(setup.getIsDone(), false);
        setup.initialise("/activity 3 /weight 100 /height 100");
        assertEquals(setup.getIsDone(), true);
        assertTrue(setup.getUser() instanceof User);
        user = setup.getUser();
        assertEquals(user.getName(), "Foo Chi Hen");
        assertEquals(user.getWeight(), 100);
        assertEquals(user.getHeight(), 100);
        assertEquals(user.getAge(), 22);
        assertEquals(user.getActivityLevel(), 3);
        assertEquals(user.getGender(), Gender.MALE);
    }

    @Test
    void userSetupTestAge() {
        user = new User();
        setup = new UserSetup(user);
        setup.initialise("/name Foo Chi Hen /weight 100 /height 100 /activity 2 /gender Male");
        assertEquals(setup.getIsDone(), false);
        setup.initialise("/age asfdasfdas");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/age -12");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/age 2113");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/age 23");
        assertEquals(setup.getIsDone(),true);
    }

    @Test
    void userSetupTestWeight() {
        user = new User();
        setup = new UserSetup(user);
        setup.initialise("/name Foo Chi Hen /age 22 /height 100 /activity 2 /gender Male");
        assertEquals(setup.getIsDone(), false);
        setup.initialise("/weight asdfasf");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/weight 0");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/weight 100");
        assertEquals(setup.getIsDone(),true);
    }

    @Test
    void userSetupTestHeight() {
        user = new User();
        setup = new UserSetup(user);
        setup.initialise("/name Foo Chi Hen /age 22 /weight 100 /activity 2 /gender Male");
        assertEquals(setup.getIsDone(), false);
        setup.initialise("/height asdasd");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/height -12");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/height 2113");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/height 100");
        assertEquals(setup.getIsDone(),true);
    }

    @Test
    void userSetupTestActivity() {
        user = new User();
        setup = new UserSetup(user);
        setup.initialise("/name Foo Chi Hen /weight 100 /height 100 /age 22 /gender Male");
        assertEquals(setup.getIsDone(), false);
        setup.initialise("/activity asdfasf");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/activity 0");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/activity 5");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/activity 3");
        assertEquals(setup.getIsDone(),true);
    }

    @Test
    void userSetupTestGender() {
        user = new User();
        setup = new UserSetup(user);
        setup.initialise("/name Foo Chi Hen /weight 100 /height 100 /age 22 /activity 2");
        assertEquals(setup.getIsDone(), false);
        setup.initialise("/gender zxcvzxcv");
        assertEquals(setup.getIsDone(),false);
        setup.initialise("/gender Male");
        assertEquals(setup.getIsDone(),true);
    }
}