package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.model.meal.MealList;
import duke.ui.Ui;
import duke.model.user.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class UpdateWeightCommand extends Command {
    private String description;

    /**
     * Constructor for UpdateWeightCommand.
     * @param description the data to update the user document with
     */
    public UpdateWeightCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the UpdateWeightCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        String[] temp = description.split("/date");
        Calendar calendarDate = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendarDate.getTime());
        if (temp.length > 1) {
            try {
                user.setWeight(Integer.parseInt(temp[0].trim()), temp[1]);
                date = temp[1];
            } catch (DukeException e) {
                ui.showMessage(e.getMessage());
            }
        } else {
            user.setWeight(Integer.parseInt(temp[0]));
        }
        try {
            storage.saveUser(user);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showWeightUpdate(user, Integer.parseInt(temp[0].trim()), date);
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }
}
