package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

import java.lang.reflect.InvocationTargetException;

public class CommandDeleteReceipt extends Command {
    protected String userInput;

    /**
     * Constructor for CommandDeleteReceipt subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandDeleteReceipt(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Deletes the specific entry that the user wants to remove. \n"
                + "FORMAT: deletereceipt <Index_of_Entry>";
        this.commandType = CommandType.DELETERECEIPT;
    }


    @Override
    public void execute(StorageManager storageManager) {
        String outputStr;
        String integer = Parser.parseForPrimaryInput(this.commandType, userInput);
        if (integer.isEmpty()) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Index input is missing. FORMAT : deletereceipt <Index_of_Entry>");
            return;
        }
        try {
            int index = Integer.parseInt(integer) - 1;
            outputStr = ("Receipt"
                    + " "
                    + (index + 1)
                    + " "
                    + "has been deleted\n");
            storageManager.deleteReceiptByIndex(index);
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid index input. Please enter an integer");
            return;
        }
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr(outputStr);
    }


}