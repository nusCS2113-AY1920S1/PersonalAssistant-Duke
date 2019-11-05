package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;

public class CommandSub extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    //Constructor

    /**
     * Constructor for CommandSub subCommand Class.
     *
     * @param userInput String is the user input from the CLI
     */
    public CommandSub(String userInput) {
        this.userInput = userInput;
        this.description = "Subtracts two double values. Format: sub <num1> / <num2>";
        this.commandType = CommandType.SUB;
    }



    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        String stringOne = Parser.parseForPrimaryInput(this.commandType, userInput);
        String stringTwo = Parser.parseForFlag("", userInput);
        try {
            this.entryOne = Double.parseDouble(stringOne);
        } catch (NumberFormatException e) {
            Ui.dukeSays("Invalid input please enter in this format: sub <num1> / <num2>");
            return;
        }
        try {
            this.entryTwo = Double.parseDouble(stringTwo);
        } catch (NumberFormatException e) {
            Ui.dukeSays("Invalid input please enter the second number. Format: sub <num1> / <num2>");
            return;
        } catch (NullPointerException e) {
            Ui.dukeSays("Enter forward slash and second number. Format: sub <num1> / <num2>");
            return;
        }
        double result = entryOne - entryTwo;
        System.out.println(result);

    }
}

