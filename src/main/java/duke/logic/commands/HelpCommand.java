package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
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

    public HelpCommand(boolean flag, String message) {
        this.isFail = true;
        this.error = message;
    }
    /**
     * Executes the HelpCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @throws DukeException when there is an error loading the help file
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        ArrayList<String> helpLines = new ArrayList<>();
        try {
            storage.loadHelp(helpLines, specifiedHelp);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showHelp(helpLines);
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }
}
