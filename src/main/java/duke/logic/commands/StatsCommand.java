package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.MealList;
import duke.model.TransactionList;
import duke.model.user.User;
import duke.storage.Storage;
import duke.ui.InputHandler;
import duke.ui.Ui;

public class StatsCommand extends Command {

    public StatsCommand() {
    }

    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user,
                        InputHandler in, TransactionList transactions) throws DukeException {
        ui.showStats(user);
    }

}
