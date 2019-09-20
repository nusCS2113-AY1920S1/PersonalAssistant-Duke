package wallet.logic;

import wallet.logic.command.Command;
import wallet.logic.parser.ParserManager;
import wallet.storage.Storage;

public class LogicManager {
    private final Storage storage;
    private final ParserManager parserManager;

    public LogicManager(Storage storage) {
        this.storage = storage;
        this.parserManager = new ParserManager();
    }

    /**
     * Executes the command and returns the result.
     * @param fullCommand The full command input by user.
     * @return
     */
    public boolean execute(String fullCommand) {
        boolean isExit = false;
        try {
            Command command = parserManager.parseCommand(fullCommand);
            isExit = command.execute();
        } catch (Exception e) {
            System.out.println("Error encountered while executing command.");
        }

        return isExit;
    }
}
