package duke.logic.conversations;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;

/**
 * Conversation manager for Duke to handle two-way communications.
 */
public class ConversationManager {
    private boolean isFinished;
    private Conversation conversation;

    public ConversationManager() {
        isFinished = true;
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
        switch (input) {
        case "done":
            conversation = new MarkDoneConversation();
            break;
        case "delete":
            conversation = new DeleteConversation();
            break;
        case "findtime":
            conversation = new FreeTimeConversation();
            break;
        case "busStop":
            conversation = new GetBusStopConversation();
            break;
        case "findPath":
            conversation = new FindPathConversation();
            break;
        case "todo":
            conversation = new ToDoConversation();
            break;
        case "find":
            conversation = new FindConversation();
            break;
        case "search":
            conversation = new SearchConversation();
            break;
        default:
            throw new DukeException(Messages.ERROR_COMMAND_UNKNOWN);
        }
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

    public boolean isFinished() {
        return isFinished;
    }
}
