package MoneyCommands;

import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;
import Money.Account;

public class DeleteIncomeCommand extends MoneyCommand {

    private String inputString;
    private int serialNo;

    public DeleteIncomeCommand(String command){
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
        if (serialNo > account.getIncomeListTotal().size()){
            throw new DukeException("The serial number of the income is Out Of Bounds!");
        }
//        System.out.println(" Noted. I've removed this income source:\n");
//        System.out.println("  " + account.getIncomeListTotal().get(serialNo-1).toString() + "\n");
//        System.out.println(" Now you have " + (account.getIncomeListTotal().size()-1) + " income sources in the list.");

        ui.appendToOutput(" Noted. I've removed this income source:\n");
        ui.appendToOutput("  " + account.getIncomeListTotal().get(serialNo-1).toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getIncomeListTotal().size()-1) + " income sources in the list.\n");

        account.getIncomeListTotal().remove(serialNo-1);
    }
}