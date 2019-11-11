package executor.command;

import duke.exception.DukeException;
import storage.StorageManager;

public class CommandDelete extends Command {
    protected String userInput;

    /**
     * Constructor for CommandDelete subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandDelete(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Deletes the specific entry that the user wants to remove. \n"
                + "FORMAT: delete <Index_of_Entry>";
        this.commandType = CommandType.DELETE;
    }

    @Override
    public void execute(StorageManager storageManager) {
        String outputStr;
        try {
            int index = Integer.parseInt(userInput.replace("delete", "").trim()) - 1;
            outputStr = ("Task"
                    + " "
                    + (index + 1)
                    + ")"
                    + storageManager.getTaskNameByIndex(index)
                    + " "
                    + "has been deleted.\n");
            storageManager.deleteTaskByIndex(index);
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
