package moneycommands;

import controlpanel.*;
import money.Account;
import moneycommands.MoneyCommand;

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
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        if (serialNo > account.getShortTermGoals().size()){
            throw new DukeException("The serial number of the task is Out Of Bounds!");
        }
//        System.out.println(" Noted. I've removed this task:\n");
//        System.out.println("  " + account.getShortTermGoals().get(serialNo-1).toString() + "\n");
//        System.out.println(" Now you have " + (account.getShortTermGoals().size()-1) + " tasks in the list.");

        ui.appendToOutput(" Noted. I've removed this Goal:\n");
        ui.appendToOutput("  " + account.getShortTermGoals().get(serialNo-1).toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getShortTermGoals().size()-1) + " goals in the list.\n");

        account.getShortTermGoals().remove(serialNo-1);
        storage.writeToFile(account);
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage moneyStorage) { return; }
}
