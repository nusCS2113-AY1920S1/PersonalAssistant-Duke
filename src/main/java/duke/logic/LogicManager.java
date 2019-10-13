package duke.logic;

import duke.commands.Command;
import duke.commands.CommandResult;
import duke.commons.exceptions.DukeException;
import duke.logic.conversations.ConversationManager;
import duke.logic.parsers.Parser;
import duke.storage.Storage;

public class LogicManager extends Logic {
    private static  final String FILE_PATH = "data/tasks.txt";
    private Storage storage;
    private ConversationManager conversationManager;

    /**
     * Creates LogicManager instance.
     */
    public LogicManager() throws DukeException {
        conversationManager = new ConversationManager();
        storage = new Storage(FILE_PATH);
    }

    /**
     * Gets response from LogicManager.
     *
     * @param userInput The input string from user.
     * @return CommandResult Object containing information for Ui to display.
     */
    public CommandResult execute(String userInput) throws DukeException {
        Command c;
        try {
            c = Parser.parseSingleCommand(userInput);
            conversationManager.clearContext();
        } catch (DukeException e) {
            //rename the above particular exception to something more meaningful
            c = getCommandFromConversationManager(userInput);
        }
        return c.execute(storage);
    }

    /**
     * Gets a command from ConversationManager.
     */
    private Command getCommandFromConversationManager(String userInput) throws DukeException {
        Command c;
        conversationManager.converse(userInput);
        if (conversationManager.isFinished()) {
            String result = conversationManager.getResult();
            c = Parser.parseComplexCommand(result);
        } else {
            String prompt = conversationManager.getPrompt();
            c = Parser.parsePromptCommand(prompt);
        }
        return c;
    }
}
