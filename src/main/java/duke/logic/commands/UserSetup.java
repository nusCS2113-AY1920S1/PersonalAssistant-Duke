package duke.logic.commands;

import duke.model.user.Gender;
import duke.model.user.User;
import duke.ui.InputHandler;
import duke.ui.UserUi;

//@@author koushireo

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
            setName(info);
        } else if (user.getAge() == 0) {
            int age = 0;
            try {
                age = in.getInt();
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
            setAge(age);
        } else if (user.getAllWeight().size() == 0) {
            int weight = 0;
            try {
                weight = in.getInt();
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
            setWeight(weight);
            user.setOriginalWeight(weight);
        } else if (user.getHeight() == 0) {
            int height = 0;
            try {
                height = in.getInt();
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
            setHeight(height);
        } else if (user.getGender() == null) {
            Gender sex = null;
            if (info.charAt(0) == 'M' || info.charAt(0) == 'm') {
                sex = Gender.MALE;
            } else if (info.charAt(0) == 'F' || info.charAt(0) == 'f') {
                sex = Gender.FEMALE;
            } else {
                ui.showMessage("Invalid gender info");
            }
            setGender(sex);
        } else if (user.getActivityLevel() == 5) {
            int activity = 0;
            try {
                activity = Integer.parseInt(info) - 1;
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
            setActivity(activity);
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
