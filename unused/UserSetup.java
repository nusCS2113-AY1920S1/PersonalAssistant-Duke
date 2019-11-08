package duke.logic.commands;

import duke.model.user.Gender;
import duke.model.user.User;
import duke.ui.InputHandler;
import duke.ui.UserUi;

//@@author koushireo-unused

/**
 * UserSetup is a public class that facilitates user profile creation.
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
            ui.showName();
        }
    }

    public void initialise(String info) {
        InputHandler in = new InputHandler(info);
        if (user.getName() == null) {
            ui.showName();
            setName(info);
        } else if (user.getAge() == 0) {
            ui.showAge();
            int age = 0;
            try {
                age = in.getInt();
                setAge(age);
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
        } else if (user.getAllWeight().size() == 0) {
            ui.showWeight();
            int weight = 0;
            try {
                weight = in.getInt();
                setWeight(weight);
                user.setOriginalWeight(weight);
            } catch (Exception e) {
                ui.showMessage("Please input a valid number for weight!");
            }
        } else if (user.getHeight() == 0) {
            ui.showHeight();
            int height = 0;
            try {
                height = in.getInt();
                setHeight(height);
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
        } else if (user.getGender() == null) {
            ui.showGender();
            Gender sex = null;
            if (info.charAt(0) == 'M' || info.charAt(0) == 'm') {
                sex = Gender.MALE;
                setGender(sex);
            } else if (info.charAt(0) == 'F' || info.charAt(0) == 'f') {
                sex = Gender.FEMALE;
                setGender(sex);
            } else {
                ui.showMessage("Invalid gender info");
            }
        } else if (user.getActivityLevel() == 5) {
            ui.showActivity();
            int activity = 5;
            try {
                activity = Integer.parseInt(info) - 1;
                setActivity(activity);
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
        }
        if (user.valid()) {
            isDone = true;
            ui.showUserSetupDone(user);
            ui.showWelcome();
        }
    }

    public void setName(String name) {
        this.user.setName(name);
        ui.showAge();
    }

    public void setAge(int age) {
        this.user.setAge(age);
        ui.showWeight();
    }

    public void setWeight(int weight) {
        this.user.setWeight(weight);
        ui.showHeight();
    }

    public void setHeight(int height) {
        this.user.setHeight(height);
        ui.showGender();
    }

    public void setGender(Gender gender) {
        this.user.setGender(gender);
        ui.showActivity();
    }

    public void setActivity(int level) {
        user.setActivityLevel(level);
    }

    public User getUser() {
        return this.user;
    }

    public boolean getIsDone() {
        return isDone;
    }
}
