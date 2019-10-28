package duke.logic.conversations;

import duke.commons.Messages;

/**
 * Handles the conversation occurring when a findTime command is entered.
 */
public class FreeTimeConversation extends Conversation {
    private static final String command = "findtime";
    private String duration;

    /**
     * Initialises the Conversation object.
     */

    public FreeTimeConversation() {
        super();
        prompt = Messages.PROMPT_FREETIME_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            duration = input;
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
        if (duration != null) {
            result = command + " " + duration;
        } else {
            attempts++;
        }
    }
}
