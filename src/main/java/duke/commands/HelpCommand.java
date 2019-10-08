package duke.commands;

import duke.exceptions.DukeException;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.user.User;

import java.util.ArrayList;

public class HelpCommand extends Command{
    public HelpCommand() {
    }

    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user) throws DukeException {
        ArrayList<String> helpLines = new ArrayList<>();
        storage.loadHelp(helpLines);
        ui.showHelp(helpLines);
    }
}
