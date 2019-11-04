package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Wallet;

public class CommandAdd extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    //Constructor

    /**
     * Constructor for CommandListMonYear subCommand Class.
     *
     * @param userInput String is the user input from the CLI
     */
    public CommandAdd(String userInput) {
        this.userInput = userInput;
        this.description = "Adds two double values. Format: add <number> // <number>";
        this.commandType = CommandType.ADD;
    }



    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        this.entryOne = Double.parseDouble(Parser.parseForPrimaryInput(this.commandType, userInput));
        this.entryTwo = Double.parseDouble(Parser.parseForFlag("", userInput));
        double sum = entryOne + entryTwo;
        System.out.println(sum);
    }

}
