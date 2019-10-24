package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.MealList;
import duke.model.TransactionList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.model.user.User;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The HelpCommand is a public class that extends from the abstract class Command.
 * It finds and shows to the UI the required help file by the user
 */
public class HelpCommand extends Command {
    private String specifiedHelp = "";

    /**
     * Constructor for HelpCommand.
     */
    public HelpCommand() {
    }

    /**
     * Constructor for HelpCommand.
     * @param specifiedHelp The type of help specified by the user
     */
    public HelpCommand(String specifiedHelp) {
        this.specifiedHelp = specifiedHelp;
    }

    /**
     * Executes the HelpCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param ui the ui object to display the results of the command to the user
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param in the scanner object to handle secondary command IO
     * @throws DukeException when there is an error loading the help file
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user,
                        Scanner in, TransactionList transactions) throws DukeException {
        ArrayList<String> helpLines = new ArrayList<>();
        storage.loadHelp(helpLines, specifiedHelp);
        ui.showHelp(helpLines);
    }
}
