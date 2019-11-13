package diyeats.logic.commands;

import diyeats.logic.parsers.ArgumentSplitter;
import diyeats.model.user.Gender;
import diyeats.model.user.User;
import diyeats.ui.UserUi;

import java.util.HashMap;

//@@author koushireo

/**
 * UserSetup is a public class that facilitates user profile creation using single line.
 */

public class UserSetup {
    private User user;
    private UserUi ui = new UserUi();
    private boolean isDone = false;

    /**
     * This is a public constructor for userSetup.
     * @param user the object that handles all user data
     */

    public UserSetup(User user) {
        this.user = user;
    }

    /**
     * Help check if user object is setup.
     */

    public void start() {
        if (user.getIsSetup()) {
            ui.showWelcomeBack(user);
            ui.showWelcome();
            this.isDone = true;
        } else {
            ui.showWelcomeNew();
        }
    }

    /**This is the class that helps user to split the info they input.
     * @param info the single line info that the user input to append the information
     */

    public void initialise(String info) {
        ui.showLine();
        HashMap<String, String> userInfo = ArgumentSplitter.splitForwardSlashArguments(info);
        if (userInfo.containsKey("name")) {
            UpdateNameCommand update = new UpdateNameCommand(userInfo.get("name"));
            update.updateUser(user);
            ui.showLine();
        } else if (user.getName() == null) {
            ui.showLackName();
            ui.showLine();
        }
        if (userInfo.containsKey("age")) {
            UpdateAgeCommand update = new UpdateAgeCommand(userInfo.get("age"));
            update.updateUser(user);
            ui.showLine();
        } else if (user.getAge() == -1) {
            ui.showLackAge();
            ui.showLine();
        }
        if (userInfo.containsKey("weight")) {
            UpdateWeightCommand update = new UpdateWeightCommand(userInfo.get("weight"));
            update.updateUser(user);
            ui.showLine();
        } else if (user.getAllWeight().size() == 0) {
            ui.showLackWeight();
            ui.showLine();
        }
        if (userInfo.containsKey("height")) {
            UpdateHeightCommand update = new UpdateHeightCommand(userInfo.get("height"));
            update.updateUser(user);
            ui.showLine();
        } else if (user.getHeight() == -1) {
            ui.showLackHeight();
            ui.showLine();
        }
        if (userInfo.containsKey("activity")) {
            UpdateActivityCommand update = new UpdateActivityCommand(userInfo.get("activity"));
            update.updateUser(user);
            ui.showLine();
        } else if (user.getActivityLevel() == 5) {
            ui.showLackActivity();
            ui.showLine();
        }
        if (userInfo.containsKey("gender")) {
            if (userInfo.get("gender").toLowerCase().charAt(0) == 'm') {
                user.setGender(Gender.MALE);
                ui.showLine();
            } else if (userInfo.get("gender").toLowerCase().charAt(0) == 'f') {
                user.setGender(Gender.FEMALE);
                ui.showLine();
            } else if (user.getGender() == null) {
                ui.showWrongGenderInfo();
                ui.showLine();
            }
        } else {
            ui.showLackGender();
            ui.showLine();
        }
        if (user.isValid()) {
            isDone = true;
            ui.showUserSetupDone(user);
            ui.showWelcome();
        }
    }

    /**
     * This is a getter method.
     * @return user object that encapsulates all of the user data
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Help check if user has input enough data to properly create.
     * an user object
     * @return boolean flag to check
     */

    public boolean getIsDone() {
        return isDone;
    }
}
