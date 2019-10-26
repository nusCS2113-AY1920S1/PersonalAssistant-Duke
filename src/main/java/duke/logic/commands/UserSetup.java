package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.user.Gender;
import duke.model.user.User;
import duke.ui.userUi;

import java.math.BigDecimal;

public class UserSetup {
    private User user;
    private userUi ui = new userUi();
    private boolean isDone = false;

    public UserSetup(User user) {
        this.user = user;
    }

    public void start() {
        if (user.getIsSetup()){
            ui.showWelcomeBack(user);
            ui.showWelcome();
            this.isDone = true;
        }
        else {
            ui.showWelcomeNew();
            ui.showName();
        }
    }

    public void initialise(String info) {
        if (user.getName() == null) {
            setName(info);
        } else if (user.getAge() == 0) {
            int age = 0;
            try {
                age = Integer.parseInt(info);
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
            setAge(age);
        } else if (user.getAllWeight().size() == 0) {
            int weight = 0;
            try {
                weight = Integer.parseInt(info);
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
            setWeight(weight);
        } else if (user.getHeight() == 0) {
            int height = 0;
            try {
                height = Integer.parseInt(info);
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
            setHeight(height);
        } else if (user.getSex() == null) {
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
        } else if (user.getHasSetMaintain() == false) {
            boolean flag = true;
            if (info.charAt(0) == 'Y' || info.charAt(0) == 'y') {
                flag = true;
            } else if (info.charAt(0) == 'N' || info.charAt(0) == 'n') {
                flag = false;
            } else {
                ui.showMessage("Invalid gender info");
            }
            setLoseWeight(flag);
            user.setHasSetMaintain(true);
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
        ui.showMaintain();
    }

    public void setLoseWeight(boolean loseWeight) {
        this.user.setLoseWeight(loseWeight);
        user.setIsSetup();
    }

    public User getUser() {
        return this.user;
    }

    public boolean getIsDone() {
        return isDone;
    }
}
