package MoneyCommands;

import controlpanel.*;
import Money.Account;

public class DeleteGoalCommand extends MoneyCommand {

    private int serialNo;

    public DeleteGoalCommand(int index){
        serialNo = index;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) throws DukeException {
        if (serialNo > account.getShortTermGoals().size()){
            throw new DukeException("The serial number of the task is Out Of Bounds!");
        }
//        System.out.println(" Noted. I've removed this task:\n");
//        System.out.println("  " + account.getShortTermGoals().get(serialNo-1).toString() + "\n");
//        System.out.println(" Now you have " + (account.getShortTermGoals().size()-1) + " tasks in the list.");

        ui.appendToOutput(" Noted. I've removed this task:\n");
        ui.appendToOutput("  " + account.getShortTermGoals().get(serialNo-1).toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getShortTermGoals().size()-1) + " tasks in the list.\n");

        account.getShortTermGoals().remove(serialNo-1);
        //storage.writeTheFile(account.getShortTermGoals());
    }
}
