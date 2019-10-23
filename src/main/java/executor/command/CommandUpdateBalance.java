package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Wallet;

public class CommandUpdateBalance extends Command {

    private Double newBalance;

    /**
     * Constructor for the CommandUpdateBalance class.
     * @param userInput The user Input from the CLI
     */
    public CommandUpdateBalance(String userInput) {
        this.userInput = userInput;
        this.commandType = Parser.parseForCommandType(this.userInput);
        this.newBalance = Double.parseDouble(Parser.parseForPrimaryInput(this.commandType, this.userInput));
    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        wallet.setBalance(this.newBalance);
    }

}
