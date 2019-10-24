package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Expenditure;
import money.Item;
import moneycommands.MoneyCommand;

/**
 * This command deletes an expenditure from the Total Expenditure List according to index.
 */
public class DeleteExpenditureCommand extends MoneyCommand {

    private String inputString;
    private int serialNo;

    /**
     * Constructor of the command which initialises the delete expenditure command
     * with the index of the item to be deleted within the user input.
     * @param command delete command inputted from user
     */
    //@@author chengweixuan
    public DeleteExpenditureCommand(String command) throws DukeException {
        try {
            inputString = command;
            String temp = inputString.replaceAll("[^0-9]", "");
            serialNo = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a numerical number as the index of the expenditure to be deleted\n");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the delete expenditure command. Takes the index of the item
     * to be deleted from the Total Expenditure List and checks for the item.
     * Deletes the item from the list if the item is found.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws DukeException When the index given is out of bounds of the list
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        if (serialNo > account.getExpListTotal().size()) {
            throw new DukeException("The serial number of the expenditure is Out Of Bounds!");
        }
        Expenditure deletedEntryExp = account.getExpListTotal().get(serialNo - 1);
        ui.appendToOutput(" Noted. I've removed this expenditure:\n");
        ui.appendToOutput("  " + deletedEntryExp.toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getExpListTotal().size() - 1) +
                " expenses in the list.\n");

        account.getExpListTotal().remove(deletedEntryExp);
        storage.addDeletedEntry(deletedEntryExp);
        storage.writeToFile(account);
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        Item deletedEntry = storage.getDeletedEntry();
        if (deletedEntry instanceof  Expenditure) {
            account.getExpListTotal().add(serialNo - 1, (Expenditure)deletedEntry);
            storage.writeToFile(account);
            ui.appendToOutput(" Last command undone: \n");
            ui.appendToOutput(account.getExpListTotal().get(serialNo - 1).toString() + "\n");
            ui.appendToOutput(" Now you have " + account.getExpListTotal().size() + " expenses listed\n");
        } else  {
            throw new DukeException("u messed up at expenditure\n");
        }
    }
}