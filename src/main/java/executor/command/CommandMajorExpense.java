package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;


public class CommandMajorExpense extends Command {
    protected String userInput;
    private String amount;

    /**
     * Constructor for CommandMajorExpense subCommand Class.
     *
     * @param userInput The user input from the CLI
     */
    public CommandMajorExpense(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Lists all major expense receipts higher than user cash input \n"
                + "FORMAT : majorexpense <integer cash input>";
        this.commandType = CommandType.MAJOREXPENSE;
        this.amount = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
    public void execute(StorageManager storageManager) {
        String outputStr = "These are your expenditures above/equal to" + " " + "$" + amount + "\n";
        if (amount.startsWith("-")) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Input integer must be positive");
            return;
        }
        try {
            outputStr += storageManager.getMajorExpense(amount);

        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid cash input. Please enter integer");
            return;
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr);
    }
}

