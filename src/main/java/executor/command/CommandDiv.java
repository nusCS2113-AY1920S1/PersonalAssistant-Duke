package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

public class CommandDiv extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    /**
     * Constructor for CommandDiv subCommand Class.
     * @param userInput String is the user input from the CLI
     */
    public CommandDiv(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Divides two double values. \nFORMAT: div <number> // <number>\n";
        this.commandType = CommandType.DIV;
    }

    @Override
    public void execute(StorageManager storageManager) {
        String stringOne = Parser.parseForPrimaryInput(this.commandType, userInput);
        String stringTwo = Parser.parseForFlag("", userInput);
        try {
            this.entryOne = Double.parseDouble(stringOne);
            if (entryOne == 0) {
                throw new DukeException("The result is zero");
            }
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid input please enter in this Format: div <num1> / <num2>");
            return;
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        try {
            this.entryTwo = Double.parseDouble(stringTwo);
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid input please enter the second number. Format: div <num1> / <num2>");
            return;
        } catch (NullPointerException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Enter forward slash and second number. Format: div <num1> / <num2>");
            return;
        }
        double result = entryOne / entryTwo;
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(stringOne
                + " / "
                + stringTwo
                + " "
                + "="
                + " "
                + result
                + "\n");
    }
}