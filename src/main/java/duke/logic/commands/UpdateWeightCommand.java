package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.TransactionList;
import duke.storage.Storage;
import duke.model.MealList;
import duke.ui.Ui;
import duke.model.user.User;

import java.util.Scanner;

public class UpdateWeightCommand extends Command {
    private String description;

    public UpdateWeightCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user,
                        Scanner in, TransactionList transactions) throws DukeException {
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
