package duke.commands;

import duke.exceptions.DukeException;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.user.User;

import java.util.ArrayList;
import java.util.Scanner;

public class HelpCommand extends Command {
    private String specifiedHelp = "";
    public HelpCommand() {
    }

    public HelpCommand(String specifiedHelp) {
        this.specifiedHelp = specifiedHelp;
    }

    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user, Scanner in) throws DukeException {
        ArrayList<String> helpLines = new ArrayList<>();
        storage.loadHelp(helpLines, specifiedHelp);
        ui.showHelp(helpLines);
    }
}
