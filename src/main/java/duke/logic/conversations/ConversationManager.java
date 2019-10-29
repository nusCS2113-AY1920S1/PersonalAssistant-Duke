package duke.logic.conversations;

import duke.commons.exceptions.DukeException;
import duke.logic.RouteManager;
import duke.logic.commands.Command;
import duke.logic.parsers.ConversationParser;
import duke.logic.parsers.Parser;
import duke.logic.parsers.PromptParser;

/**
 * Manages two-way communications between SGTravel and the user.
 */
public class ConversationManager {
    private boolean isFinished;
    private boolean isInConversation;
    private Conversation conversation;
    private RouteManager routeManager;

    /**
     * Constructs the ConversationManager object and include the RouteManager.
     *
     * @param routeManager The RouteManager object.
     */
    public ConversationManager(RouteManager routeManager) {
        isFinished = true;
        isInConversation = false;
        this.routeManager = routeManager;
    }

    /**
     * Starts or continues a conversation with Duke.
     *
     * @param input The user input.
     */
    public void converse(String input) throws DukeException {
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
            isInConversation = false;
        }
    }

    /**
     * Tries to start a conversation. If the conversation is still ongoing, nothing happens.
     *
     * @param input The user input from ui.
     * @return true if a conversation is started, false otherwise.
     * @throws DukeException If no conversation could be started.
     */
    private boolean tryStartConversation(String input) throws DukeException {
        if (isFinished) {
            startConversation(input);
            isFinished = false;
            isInConversation = true;
            return true;
        }
        return false;
    }

    /**
     * Creates a Conversation object based on input.
     *
     * @param input The words from user input.
     */
    private void startConversation(String input) throws DukeException {
        conversation = ConversationParser.parse(input, routeManager);
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

    /**
     * Returns whether there is an ongoing conversation.
     *
     * @return Whether there is an ongoing conversation.
     */
    public boolean isInConversation() {
        return isInConversation;
    }
}
