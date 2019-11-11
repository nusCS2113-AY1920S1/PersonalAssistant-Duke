package diyeats.logic.command;

import diyeats.logic.commands.UpdateActivityCommand;
import diyeats.logic.commands.UserSetup;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * This class is test to test UpdateActivityCommand to check for rejections and acceptance.
 */

//@@author koushireo
public class UpdateActivityCommandTest {
    private User user = new User();
    private UserSetup setup = new UserSetup(user);
    private Wallet wallet = new Wallet();
    private Storage storage = new Storage();
    private MealList meals = new MealList();
    private Undo undo = new Undo();

    /**
     * This function helps to set up profile.
     * @param user the user object that encapsulates all of the user data
     */

    public void setProfile(User user) {
        setup.initialise("/name Foo Chi Hen /age 22 /height 100 /weight 100 /gender male /activity 2");
        user = setup.getUser();
    }

    /**
     * This tests tests for boundaries and acceptance and invalid inputs.
     */

    @Test
    public void updateActivityCommandTest() {
        setProfile(this.user);                                                   //setting up profile
        assertEquals(this.user.getActivityLevel(), 2);

        UpdateActivityCommand c1 = new UpdateActivityCommand("asdf"); //test for invalid input
        c1.execute(meals, storage, user, wallet, undo);
        assertEquals(this.user.getActivityLevel(), 2);

        c1 = new UpdateActivityCommand("0");                          //test for lower boundary
        c1.execute(meals, storage, user, wallet, undo);
        assertEquals(this.user.getActivityLevel(), 2);

        c1 = new UpdateActivityCommand("6");                          //test for upper boundary
        c1.execute(meals, storage, user, wallet, undo);
        assertEquals(this.user.getActivityLevel(), 2);

        c1 = new UpdateActivityCommand("4");                          //test for acceptance
        c1.execute(meals, storage, user, wallet, undo);
        assertEquals(this.user.getActivityLevel(), 4);
    }
}

