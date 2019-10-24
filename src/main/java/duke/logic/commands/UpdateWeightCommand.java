package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.model.MealList;
import duke.ui.Ui;
import duke.model.user.User;

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
     * @param ui the ui object to display the results of the command to the user
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param in the scanner object to handle secondary command IO
     * @throws DukeException when there is an error parsing the date
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user, Scanner in) throws DukeException {
        String[] temp = description.split("/date");
        if (temp.length > 1) {
            try {
                user.setWeight(Integer.parseInt(temp[0].trim()), temp[1]);
            } catch (DukeException e) {
                throw new DukeException(e.getMessage());
            }
        } else {
            user.setWeight(Integer.parseInt(temp[0]));
        }
        storage.saveUser(user);
    }
}
