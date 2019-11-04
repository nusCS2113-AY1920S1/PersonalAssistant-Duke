package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Wallet;

public class CommandMul extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    //Constructor

    /**
     * Constructor for CommandListMonYear subCommand Class.
     *
     * @param userInput String is the user input from the CLI
     */
    public CommandMul(String userInput) {
        this.userInput = userInput;
        this.description = "Multiplies two double values. Format: mul <number> // <number>";
        this.commandType = CommandType.MUL;
    }



    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        this.entryOne = Double.parseDouble(Parser.parseForPrimaryInput(this.commandType, userInput));
        this.entryTwo = Double.parseDouble(Parser.parseForFlag("/", userInput));
        double result = entryOne * entryTwo;
        System.out.println(result);
    }

}

