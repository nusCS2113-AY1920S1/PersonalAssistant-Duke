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

    public UserSetup(User user) {
        this.user = user;
    }

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
        HashMap<String, String> userInfo = ArgumentSplitter.splitForwardSlashArguments(info);
        if (userInfo.containsKey("name")) {
            UpdateNameCommand update = new UpdateNameCommand(userInfo.get("name"));
            update.updateUser(user);
        } else if (user.getName() == null) {
            ui.showLackName();
        }
        if (userInfo.containsKey("age")) {
            UpdateAgeCommand update = new UpdateAgeCommand(userInfo.get("age"));
            update.updateUser(user);
        } else if (user.getAge() == -1) {
            ui.showLackAge();
        }
        if (userInfo.containsKey("weight")) {
            UpdateWeightCommand update = new UpdateWeightCommand(userInfo.get("weight"));
            update.updateUser(user);
        } else if (user.getAllWeight().size() == 0) {
            ui.showLackWeight();
        }
        if (userInfo.containsKey("height")) {
            UpdateHeightCommand update = new UpdateHeightCommand(userInfo.get("height"));
            update.updateUser(user);
        } else if (user.getHeight() == -1) {
            ui.showLackHeight();
        }
        if (userInfo.containsKey("activity")) {
            UpdateActivityCommand update = new UpdateActivityCommand(userInfo.get("activity"));
            update.updateUser(user);
        } else if (user.getActivityLevel() == 5) {
            ui.showLackActivity();
        }
        if (userInfo.containsKey("gender")) {
            if (userInfo.get("gender").toLowerCase().charAt(0) == 'm') {
                user.setGender(Gender.MALE);
            } else if (userInfo.get("gender").toLowerCase().charAt(0) == 'f') {
                user.setGender(Gender.FEMALE);
            } else if (user.getGender() == null) {
                ui.showWrongGenderInfo();
            }
        } else {
            ui.showLackGender();
        }
        if (user.valid()) {
            isDone = true;
            ui.showUserSetupDone(user);
            ui.showWelcome();
        }
    }

    public User getUser() {
        return this.user;
    }

    public boolean getIsDone() {
        return isDone;
    }
}
