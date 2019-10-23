package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import moneycommands.MoneyCommand;

/**
 * This command deletes a loan from the Loans List according to index.
 */
public class DeleteLoanCommand extends MoneyCommand {

    private String inputString;
    private int serialNo;

    /**
     * Constructor of the command which initialises the delete expenditure command
     * with the index of the item to be deleted within the user input.
     * @param command delete command inputted from user
     */
    //@@author chengweixuan
    public DeleteLoanCommand(String command) {
        inputString = command;
        String temp = inputString.replaceAll("[^0-9]", "");
        serialNo = Integer.parseInt(temp);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the delete loan command. Takes the index of the item
     * to be deleted from the Loans List and checks for the item.
     * Deletes the item from the list if the item is found.
     * Note that the index given is for the Total Loans List and not the Outgoing Loans List
     * or Incoming Loans List.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws DukeException When the index given is out of bounds of the list
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        if (serialNo > account.getLoans().size()) {
            throw new DukeException("The serial number of the loan is Out Of Bounds!");
        }

        String typeStr = account.getLoans().get(serialNo - 1).getType().toString().toLowerCase();
        ui.appendToOutput(" Noted. I've removed this " + typeStr + " loan:\n");
        ui.appendToOutput("  " + account.getLoans().get(serialNo - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getLoans().size() - 1) + " total loans.\n");

        storage.markDeletedEntry("LOA", serialNo);
        account.getLoans().remove(serialNo - 1);
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        storage.undoDeletedEntry(account, "EXP", serialNo);
        storage.writeToFile(account);
        ui.appendToOutput(" Last command undone: \n");
        ui.appendToOutput(account.getExpListTotal().get(serialNo - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getExpListTotal().size() + " expenses listed\n");
    }
}