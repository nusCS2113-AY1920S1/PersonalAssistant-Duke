package MoneyCommands;

import controlpanel.*;
import Money.Account;

/**
 * This command lists all short-term goals within the Short-Term Goal List to the user
 */
public class ListGoalsCommand extends MoneyCommand{

    /**
     * Constructor of the list command
     */
    public ListGoalsCommand(){
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the list goals command.
     * Displays all short-term goals in the Short-Term Goals List to the user according to index
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     */
    @Override
    public void execute(Account account, Ui ui, Storage storage) {
        for (int i = 1; i <= account.getShortTermGoals().size();i++) {
            //System.out.println(" " + i + "." + account.getShortTermGoals().get(i-1).toString() + "\n");
            ui.appendToOutput(" " + i + "." + account.getShortTermGoals().get(i-1).toString() + "\n");
        }
        //System.out.println("current Goal Savings: $" + account.getGoalSavings());
        ui.appendToOutput("current Goal Savings: $" + account.getGoalSavings() + "\n");

    }
}
