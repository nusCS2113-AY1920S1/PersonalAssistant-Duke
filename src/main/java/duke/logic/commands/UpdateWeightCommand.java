package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.time.LocalDate;
import java.util.HashMap;

//@@author koushireo

public class UpdateWeightCommand extends Command {
    private String weight;


    public UpdateWeightCommand() {
        this.weight = null;
        this.currentDate = null;
    }

    /**
     * Constructor for UpdateWeightCommand.
     * @param weight the data to update the user document with
     */

    public UpdateWeightCommand(String weight) {
        this.weight = weight;
        this.currentDate = LocalDate.now();
    }

    public UpdateWeightCommand(String weight, String date) {
        this.weight = weight;
        this.currentDate = LocalDate.parse(date, dateFormat);
    }

    public UpdateWeightCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the UpdateWeightCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        switch (stage) {
            case 0:
                stage0(user, storage);
                break;
            case 1:
                stage1(user, storage);
                break;
            default:
                isDone = true;
        }
        stage++;
    }

    public void stage0(User user, Storage storage) {
        ui.showLine();
        HashMap<LocalDate, Double> allWeight = user.getAllWeight();
        if (!allWeight.containsKey(currentDate)) {
            try {
                user.setWeight(Integer.parseInt(weight), currentDate);
                ui.showWeightUpdate(user, Integer.parseInt(weight), currentDate);
                ui.showLine();
            } catch (NumberFormatException e) {
                ui.showMessage("Please input a proper number for weight");
            }
        } else {
            try {
                int temp = Integer.parseInt(weight);
                isDone = false;
                ui.showConfirmation(weight, currentDate);
            } catch (NumberFormatException e) {
                ui.showMessage("Please input a proper number for weight");
            }
        }
        try {
            storage.updateUser(user);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    public void stage1(User user, Storage storage) {
        ui.showLine();
        if (this.responseStr.equals("y")) {
            try {
                user.setWeight(Integer.parseInt(weight), currentDate);
                ui.showWeightUpdate(user, Integer.parseInt(weight), currentDate);
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
            isDone = true;
        } else if (this.responseStr.equals("n")) {
            ui.showRejected();
            isDone = true;
        } else {
            ui.showMessage("Please enter either Y/N");
            stage -= 1;
        }
        try {
            storage.updateUser(user);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    public void setResponseStr(String response) {
        this.responseStr = response.toLowerCase().substring(0,1);
    }
}
