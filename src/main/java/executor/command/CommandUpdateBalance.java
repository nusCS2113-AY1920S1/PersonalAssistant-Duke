package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;
import java.text.DecimalFormat;

public class CommandUpdateBalance extends Command {

    /**
     * Constructor for the CommandUpdateBalance class.
     * @param userInput The user Input from the CLI
     */
    public CommandUpdateBalance(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.SETBALANCE;
        this.description = "Updates current balance to new balance in the wallet "
                + "FORMAT : setbalance $<amount>";
    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {

        if(getExecutedCommands().contains(this.commandType.toString())){
            Ui.dukeSays("SetBalance can only be executed once!");
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            Double userAmount = extractAmount();
            if(userAmount != null) {
                wallet.setBalance(userAmount);
                Ui.dukeSays("Balance updated to: $" + decimalFormat.format(userAmount));
                Ui.printSeparator();
                getExecutedCommands().add(this.commandType.toString());
            }
        }
    }

    private Double extractAmount() {
        String incomeStr = Parser.parseForPrimaryInput(this.commandType, this.userInput);
        try {
            incomeStr = incomeStr.trim().replace("$", "");
            Double amount = Double.parseDouble(incomeStr);
            if (amount < 0) {
                throw new Exception();
            }
            return amount;
        } catch (Exception e) {
            Ui.dukeSays("Please kindly follow the format : setbalance $<amount> \n"
            + "Please enter an amount greater than or equal to zero in your wallet !\n");
            return null;
        }
    }
}
