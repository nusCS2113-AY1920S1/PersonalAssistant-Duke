package executor.command;

import interpreter.Parser;
import storage.StorageManager;

public class CommandSub extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    /**
     * Constructor for CommandSub subCommand Class.
     *
     * @param userInput String is the user input from the CLI
     */
    public CommandSub(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Subtracts two double values. Format: sub <num1> / <num2>";
        this.commandType = CommandType.SUB;
    }

    @Override
    public void execute(StorageManager storageManager) {
        String stringOne = Parser.parseForPrimaryInput(this.commandType, userInput);
        String stringTwo = Parser.parseForFlag("", userInput);
        try {
            this.entryOne = Double.parseDouble(stringOne);
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid input please enter in this format: sub <num1> / <num2>");
            return;
        }
        try {
            this.entryTwo = Double.parseDouble(stringTwo);
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid input please enter the second number. Format: sub <num1> / <num2>");
            return;
        } catch (NullPointerException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Enter forward slash and second number. Format: sub <num1> / <num2>");
            return;
        }
        double result = entryOne - entryTwo;
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(stringOne
                + " - "
                + stringTwo
                + " "
                + "="
                + " "
                + result
                + "\n");
    }
}

