package moneycommands;

import controlpanel.*;
import money.Account;
import money.Expenditure;
import money.Goal;
import money.Item;
import moneycommands.MoneyCommand;

import java.text.ParseException;

/**
 * This command deletes a short-term goal from the Short-Term Goal List according to index
 */
public class DeleteGoalCommand extends MoneyCommand {

    private int serialNo;

    /**
     * Constructor of the command which initialises the delete short-term goal command
     * with the index of the item to be deleted within the user input
     * @param index delete command inputted from user
     */
    //@@author therealnickcheong
    public DeleteGoalCommand(int index){
        serialNo = index;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the delete short-term goal command. Takes the index of the item
     * to be deleted from the Short-Term Goals List and checks for the item
     * Deletes the item from the list if the item is found
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws DukeException When the index given is out of bounds of the list
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        if (serialNo > account.getShortTermGoals().size()){
            throw new DukeException("The serial number of the task is Out Of Bounds!");
        }
        Goal deletedEntryG = account.getShortTermGoals().get(serialNo - 1);
        ui.appendToOutput(" Noted. I've removed this Goal:\n");
        ui.appendToOutput("  " + deletedEntryG.toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getShortTermGoals().size()-1) + " goals in the list.\n");


        account.getShortTermGoals().remove(deletedEntryG);
        storage.addDeletedEntry(deletedEntryG);
        storage.writeToFile(account);
        //account.sortShortTermGoals(account.getShortTermGoals());

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
            throw new DukeException("U messed up at goals\n");
        }
    }
}
