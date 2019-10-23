package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.MealList;
import duke.model.TransactionList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.model.user.User;

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
    public void execute(MealList tasks, Ui ui, Storage storage, User user, Scanner in, TransactionList transactions) throws DukeException {
        ArrayList<String> helpLines = new ArrayList<>();
        storage.loadHelp(helpLines, specifiedHelp);
        ui.showHelp(helpLines);
    }
}
