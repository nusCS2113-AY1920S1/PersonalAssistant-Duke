package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

public class CommandFind extends Command {
    protected String userInput;

    /**
     * Constructor for CommandFind subCommand Class.
     * @param userInput The User Input to be translated into a Command
     */
    public CommandFind(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Parses input and loops through list of entries and checks if input matches any of them \n"
                + "FORMAT: Find <taskName>";
        this.commandType = CommandType.FIND;
    }

    @Override
    public void execute(StorageManager storageManager) {
        StringBuilder outputStr = new StringBuilder();
        try {
            String queryInput = Parser.removeStr("find", this.userInput);
            queryInput = queryInput.toLowerCase();
            outputStr.append("Duke: Here are the Tasks matching your query '")
                    .append(queryInput)
                    .append("'.\n")
                    .append(storageManager.getTasksByName(queryInput).getPrintableTasks());
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
        }
    }
}
