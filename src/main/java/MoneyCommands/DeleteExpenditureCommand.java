package MoneyCommands;

import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;
import Money.Account;

public class DeleteExpenditureCommand extends MoneyCommand {

    private String inputString;
    private int serialNo;

    public DeleteExpenditureCommand(String command){
        inputString = command;
        String temp = inputString.replaceAll("[^0-9]", "");
        serialNo = Integer.parseInt(temp);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) throws DukeException {
        if (serialNo > account.getExpListTotal().size()){
            throw new DukeException("The serial number of the expenditure is Out Of Bounds!");
        }
//        System.out.println(" Noted. I've removed this expenditure:\n");
//        System.out.println("  " + account.getExpListTotal().get(serialNo-1).toString() + "\n");
//        System.out.println(" Now you have " + (account.getExpListTotal().size()-1) + " expenditures in the list.");

        ui.appendToOutput(" Noted. I've removed this expenditure:\n");
        ui.appendToOutput("  " + account.getExpListTotal().get(serialNo-1).toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getExpListTotal().size()-1) + " expenditures in the list.\n");

        account.getExpListTotal().remove(serialNo-1);
    }
}