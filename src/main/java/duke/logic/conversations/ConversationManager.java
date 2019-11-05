package duke.logic.conversations;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.parsers.ConversationParser;
import duke.logic.parsers.Parser;
import duke.logic.parsers.commandparser.PromptParser;

import java.util.logging.Logger;

/**
 * Manages two-way communications between SGTravel and the user.
 */
public class ConversationManager {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private boolean isFinished;
    private Conversation conversation;

    /**
     * Constructs the ConversationManager object and include the RouteManager.
     */
    public ConversationManager() {
        isFinished = true;
    }

    /**
     * Starts or continues a conversation with Duke.
     *
     * @param input The user input.
     * @throws ParseException If no conversation could be started.
     */
    public void converse(String input) throws ParseException {
        if (tryStartConversation(input)) {
            return;
        }
        conversation.execute(input);
        tryEndConversation();
    }

    /**
     * Tries to end a conversation. If the conversation is still ongoing, nothing happens.
     */
    private void tryEndConversation() {
        if (conversation.isFinished()) {
            isFinished = true;
        }
    }

    /**
     * Tries to start a conversation. If the conversation is still ongoing, nothing happens.
     *
     * @param input The user input from ui.
     * @return true if a conversation is started, false otherwise.
     * @throws ParseException If no conversation could be started.
     */
    private boolean tryStartConversation(String input) throws ParseException {
        if (isFinished) {
            startConversation(input);
            isFinished = false;
            return true;
        }
        return false;
    }

    /**
     * Creates a Conversation object based on input.
     *
     * @param input The words from user input.
     * @throws ParseException If the input cannot be parsed.
     */
    private void startConversation(String input) throws ParseException {
        conversation = ConversationParser.parse(input);
    }

    /**
     * Gets a command from the ConversationManager.
     * @return Command for logic to execute.
     * @throws DukeException If the result could not be parse by parser.
     */
    public Command getCommand() throws DukeException {
        if (isFinished) {
            return Parser.parseComplexCommand(getResult());
        }
        return PromptParser.parseCommand(getPrompt());
    }

    /**
     * Gets the result of the conversation.
     *
     * @return result The String result made from Conversation.
     */
    public String getResult() {
        String result = conversation.getResult();
        clearContext();
        return result;
    }

    /**
     * Gets the prompt from Conversation object if applicable.
     *
     * @return The prompt.
     */
    public String getPrompt() {
        assert (conversation != null) : "Conversation should not be null";
        return conversation.getPrompt();
    }

    /**
     * Clears the current context of the conversation.
     */
    public void clearContext() {
        isFinished = true;
        conversation = null;
    }

    /**
     * Returns whether the current conversation is finished.
     *
     * @return Whether the current conversation is finished.
     */
    public boolean isFinished() {
        return isFinished;
    }
}
