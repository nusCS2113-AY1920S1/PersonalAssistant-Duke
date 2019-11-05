package executor.command;

import interpreter.Parser;
import storage.StorageManager;

public class CommandSub extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    /**
     * Constructor for CommandListMonYear subCommand Class.
     *
     * @param userInput String is the user input from the CLI
     */
    public CommandSub(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Subtracts two double values. Format: sub <number> // <number>";
        this.commandType = CommandType.SUB;
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
        double result = entryOne - entryTwo;
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr(entryOneStr
                + " - "
                + entryTwoStr
                + result
                + "\n");
    }
}

