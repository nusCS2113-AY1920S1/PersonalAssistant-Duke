package duke.commands;

import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.user.User;

import java.util.Scanner;

public class UpdateWeightCommand extends Command {
    private String description;

    public UpdateWeightCommand(String description) {
        this.description = description;
    }

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
