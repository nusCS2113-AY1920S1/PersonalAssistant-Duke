package duke.logic.conversations;

import duke.commons.Messages;

/**
 * Handles the conversation occurring when a delete command is entered.
 */
public class DeleteConversation extends Conversation {
    private static final String command = "delete";
    private String index;

    /**
     * Initialises the Conversation object.
     */
    public DeleteConversation() {
        super();
        prompt = Messages.PROMPT_DELETE_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            index = input;
            buildResult();
        }

        tryCancelConversation(input);
    }

    /**
     * Gets result of prompt.
     *
     * @return result The result.
     */
    @Override
    protected void buildResult() {
        if (index != null) {
            result = command + " " + index;
            setFinished(true);
        } else {
            attempts++;
        }
    }
}
