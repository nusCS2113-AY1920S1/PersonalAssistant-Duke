package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import storage.StorageManager;
import ui.Wallet;

public class CommandAdd extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    //Constructor

    /**
     * Constructor for CommandListMonYear subCommand Class.
     * @param userInput String is the user input from the CLI
     */
    public CommandAdd(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Adds two double values. Format: add <number> // <number>";
        this.commandType = CommandType.ADD;
    }

    @Override
    public void execute(StorageManager storageManager) {
        String entryOneStr = Parser.parseForPrimaryInput(this.commandType, userInput);
        if (entryOneStr.equals("")) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Missing Numerator. \n FORMAT: div <number> // <number>\n");
            return;
        }
        String entryTwoStr = Parser.parseForFlag("", userInput);
        if (entryTwoStr == null) {
            this.entryTwo = 0.0;
        } else {
            this.entryTwo = Double.parseDouble(entryTwoStr);
        }
        double sum = entryOne + entryTwo;
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr(entryOneStr
                + " + "
                + entryTwoStr
                + sum
                + "\n");
    }

}
