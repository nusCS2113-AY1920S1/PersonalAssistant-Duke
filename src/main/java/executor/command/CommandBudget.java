package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

import java.text.DecimalFormat;

public class CommandBudget extends Command {

    private Double budgetAmount;

    public CommandBudget(String userInput) {
        super();
        this.userInput = userInput;
        this.description = " Sets user budget \n"
                + "FORMAT : budget $<amount>\n";
        this.commandType =  CommandType.BUDGET;
    }

    @Override
    public void execute(StorageManager storageManager) {
        try {
            this.budgetAmount = extractAmount();
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        storageManager.getWallet().getReceipts().setBudget(this.budgetAmount);
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr("Budget updated to: $" + decimalFormat.format(this.budgetAmount) + "\n");

    }

    private Double extractAmount() throws DukeException {
        String userBudget = Parser.parseForPrimaryInput(this.commandType, this.userInput);
        try {
            userBudget = userBudget.trim().replace("$", "");
            Double amount = Double.parseDouble(userBudget);
            if (amount <= 0) {
                throw new Exception();
            }
            return amount;
        } catch (Exception e) {
            throw new DukeException("Please kindly follow the format : budget $<amount> \n"
                    + "Please enter an amount greater than zero!\n");
        }
    }
}
