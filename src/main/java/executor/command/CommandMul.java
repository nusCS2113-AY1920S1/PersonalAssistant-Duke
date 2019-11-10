package executor.command;

import interpreter.Parser;
import storage.StorageManager;

public class CommandMul extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    /**
     * Constructor for CommandMul subCommand Class.
     *
     * @param userInput String is the user input from the CLI
     */
    public CommandMul(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Multiplies two double values. Format: mul <num1> / <num2>";
        this.commandType = CommandType.MUL;
    }

    @Override
    public void execute(StorageManager storageManager) {
        String stringOne = Parser.parseForPrimaryInput(this.commandType, userInput);
        String stringTwo = Parser.parseForFlag("", userInput);
        try {
            this.entryOne = Double.parseDouble(stringOne);
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid input please enter in this format: mul <num1> / <num2>");
            return;
        }
        try {
            this.entryTwo = Double.parseDouble(stringTwo);
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid input please enter the second number. Format: mul <num1> / <num2>");
            return;
        } catch (NullPointerException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Enter forward slash and second number. Format: mul <num1> / <num2>");
            return;
        }
        double result = entryOne * entryTwo;
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(stringOne
                + " * "
                + stringTwo
                + " "
                + "="
                + " "
                + result
                + "\n");
    }
}