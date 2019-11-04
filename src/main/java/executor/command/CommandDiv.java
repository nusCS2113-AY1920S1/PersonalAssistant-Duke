package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Wallet;

public class CommandDiv extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    //Constructor

    /**
     * Constructor for CommandListMonYear subCommand Class.
     *
     * @param userInput String is the user input from the CLI
     */
    public CommandDiv(String userInput) {
        this.userInput = userInput;
        this.description = "Divides two double values. Format: div <number> // <number>";
        this.commandType = CommandType.DIV;
    }



    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        this.entryOne = Double.parseDouble(Parser.parseForPrimaryInput(this.commandType, userInput));
        this.entryTwo = Double.parseDouble(Parser.parseForFlag("/", userInput));
        double result = entryOne / entryTwo;
        System.out.println(result);
    }

}

