package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UpdateWeightCommand extends Command {
    private String description;
    private String weight;
    private String date;
    /**
     * Constructor for UpdateWeightCommand.
     * @param description the data to update the user document with
     */

    public UpdateWeightCommand(String description) {
        this.description = description;
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
        String[] temp = description.split("/date");
        Calendar calendarDate = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendarDate.getTime());
        weight = temp[0].trim();
        HashMap<String, Double> allWeight = user.getAllWeight();
        if (temp.length > 1) {
            date = temp[1];
        }
        if (!allWeight.containsKey(date)) {
            try {
                user.setWeight(Integer.parseInt(weight), date);
                ui.showWeightUpdate(user, Integer.parseInt(weight), date);
                ui.showLine();
            } catch (DukeException e) {
                ui.showMessage(e.getMessage());
            }
        } else {
            isDone = false;
            ui.showConfirmation(weight, date);
            ui.showLine();
        }
        try {
            storage.updateUser(user);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
    }

    public void stage1(User user, Storage storage) {
        ui.showLine();
        if (this.responseStr.equals("y")) {
            try {
                user.setWeight(Integer.parseInt(weight), date);
                ui.showWeightUpdate(user, Integer.parseInt(weight), date);
            } catch (DukeException e) {
                ui.showMessage(e.getMessage());
            }
        } else {
            ui.showRejected();
        }
        isDone = true;
        try {
            storage.updateUser(user);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    public void setResponse(String response) {
        this.responseStr = response.toLowerCase().substring(0,1);
    }
}
