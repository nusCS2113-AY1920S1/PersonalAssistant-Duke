package executor.command;

import interpreter.Parser;
import storage.StorageManager;

public class CommandDiv extends Command {
    private String userInput;
    private double entryOne;
    private double entryTwo;

    /**
     * Constructor for CommandListMonYear subCommand Class.
     *
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
        String entryOneStr = Parser.parseForPrimaryInput(this.commandType, userInput);
        if (entryOneStr.equals("")) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Missing Numerator. \n FORMAT: div <number> // <number>\n");
            return;
        }
        this.entryOne = Double.parseDouble(entryOneStr);
        String entryTwoStr = Parser.parseForFlag("", userInput);
        if (entryTwoStr == null) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Missing Divisor. \n FORMAT: div <number> // <number>\n");
            return;
        }
        this.entryTwo = Double.parseDouble(entryTwoStr);
        double result = entryOne / entryTwo;
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(entryOneStr
                + " / "
                + entryTwoStr
                + result
                + "\n");
    }

}

