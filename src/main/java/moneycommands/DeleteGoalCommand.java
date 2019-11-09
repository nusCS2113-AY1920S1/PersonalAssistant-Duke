package moneycommands;

import controlpanel.MoneyStorage;
import controlpanel.Ui;
import controlpanel.DukeException;
import money.Account;
import money.Expenditure;
import money.Goal;
import money.Item;
import moneycommands.MoneyCommand;

import java.text.ParseException;

/**
 * This command deletes a short-term goal from the Short-Term Goal List according to index.
 */
public class DeleteGoalCommand extends MoneyCommand {

    private int serialNo;
    private String inputString;

    //@@author therealnickcheong
    /**
     * Constructor of the command which initialises the delete goal command.
     * with the index of the item to be deleted within the user input.
     * @param cmd delete command inputted from user.
     */
    public DeleteGoalCommand(String cmd) throws DukeException {
        inputString = cmd;
        try {
            String temp = inputString.replaceAll("[^-?0-9]", "");
            serialNo = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter in the format: "
                    + "done goal <index>\n");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the delete goal command. Takes the index of the item.
     * to be deleted from the Goals List and checks for the item.
     * Deletes the item from the list if the item is found.
     * @param account Account object containing all financial info of user saved on the programme.
     * @param ui Handles interaction with the user.
     * @param storage Saves and loads data into/from the local disk.
     * @throws DukeException When the index given is out of bounds of the list.
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        if (serialNo > account.getShortTermGoals().size() || serialNo <= 0) {
            throw new DukeException("The serial number of the task is Out Of Bounds!");
        }
        Goal deletedEntryG = account.getShortTermGoals().get(serialNo - 1);
        ui.appendToOutput(" Noted. I've removed this Goal:\n");
        ui.appendToOutput("  " + deletedEntryG.toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getShortTermGoals().size() - 1) + " goals in the list.\n");


        account.getShortTermGoals().remove(serialNo - 1);
        storage.addDeletedEntry(deletedEntryG);
        storage.writeToFile(account);

        MoneyCommand list = new ListGoalsCommand();
        list.execute(account,ui,storage);
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        Item deletedEntry = storage.getDeletedEntry();
        if (deletedEntry instanceof Goal) {
            account.getShortTermGoals().add(serialNo - 1, (Goal)deletedEntry);
            storage.writeToFile(account);
            ui.appendToOutput(" Last command undone: \n");
            ui.appendToOutput(account.getShortTermGoals().get(serialNo - 1).toString() + "\n");
            ui.appendToOutput(" Now you have " + account.getShortTermGoals().size() + " goals listed\n");

            MoneyCommand list = new ListGoalsCommand();
            list.execute(account, ui, storage);
        } else {
            throw new DukeException("Last deleted entry is of invalid type!!\n");
        }
    }
}
