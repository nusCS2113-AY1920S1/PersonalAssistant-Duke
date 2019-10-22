package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.MealList;
import duke.model.Transaction;
import duke.model.user.User;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Scanner;

public class AddTransactionCommand extends Command {
    private Transaction transaction;

    public AddTransactionCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user, Scanner in) throws DukeException {

    }
}
