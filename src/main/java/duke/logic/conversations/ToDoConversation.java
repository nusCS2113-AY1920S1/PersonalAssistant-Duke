package duke.logic.conversations;

import duke.commons.Messages;

/**
 * Handles the conversation occurring when a todo command is entered.
 */
public class ToDoConversation extends Conversation {
    private static final String command = "todo";
    private String description;

    /**
     * Initialises the Conversation object.
     */
    public ToDoConversation() {
        super();
        prompt = Messages.PROMPT_TODO_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        description = input;
        buildResult();
    }

    /**
     * Gets result of prompt.
     *
     * @return result The result.
     */
    @Override
    protected void buildResult() {
        if (description != null) {
            result = command + " " + description;
            setFinished(true);
        } else {
            attempts++;
        }
    }
}
