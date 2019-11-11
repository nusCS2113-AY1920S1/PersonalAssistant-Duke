package executor.command;

import interpreter.Parser;
import storage.StorageManager;

public class CommandAdd extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    /**
     * Constructor for CommandAdd subCommand Class.
     * @param userInput String is the user input from the CLI
     */
    public CommandAdd(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Adds two double values. Format: add <num1> / <num2>";
        this.commandType = CommandType.ADD;
    }


    @Override
    public void execute(StorageManager storageManager) {
        String stringOne = Parser.parseForPrimaryInput(this.commandType, userInput);
        String stringTwo = Parser.parseForFlag("", userInput);
        try {
            this.entryOne = Double.parseDouble(stringOne);
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid input please enter in this format: add <num1> / <num2>");
            return;
        }
        try {
            this.entryTwo = Double.parseDouble(stringTwo);
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid input please enter the second number. Format: add <num1> / <num2>");
            return;
        } catch (NullPointerException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Enter forward slash and second number. Format: add <num1> / <num2>");
            return;
        }
        double sum = entryOne + entryTwo;
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(stringOne
                + " + "
                + stringTwo
                + " "
                + "="
                + " "
                + sum
                + "\n");
    }
}
