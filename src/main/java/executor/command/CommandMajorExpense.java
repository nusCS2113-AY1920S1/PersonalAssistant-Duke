package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;


public class CommandMajorExpense extends Command {
    private String amount;

    /**
     * Constructor for CommandMajorExpense subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandMajorExpense(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Lists all major expense receipts higher than user cash input \n"
                + "FORMAT : majorexpense <positive integer cash input>"
                + "\n"
                + "Lists all major expenses above/equal to $100\n"
                + "FORMAT : majorexpense";
        this.commandType = CommandType.MAJOREXPENSE;
        this.amount = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
    public void execute(StorageManager storageManager) {
        String outputStr = "These are your receipts above/equal to" + " " + "$" + amount + "\n";
        String output = "These are your receipts above/equal to $" + 100 + "\n";
        if (amount.startsWith("-")) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Input integer must be positive");
            return;
        }
        if (amount.isEmpty()) {
            try {
                output += storageManager.getMajorReceipt();
            } catch (DukeException e) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr(e.getMessage());
                return;
            }
            this.infoCapsule.setCodeCli();
            this.infoCapsule.setOutputStr(output);
        } else {
            try {
                outputStr += storageManager.getMajorExpense(amount);
            } catch (DukeException e) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr(e.getMessage());
                return;
            }
            this.infoCapsule.setCodeCli();
            this.infoCapsule.setOutputStr(outputStr);
        }
    }
}

