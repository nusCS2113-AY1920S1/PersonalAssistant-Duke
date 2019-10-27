package duke.logic.conversations;

import duke.commons.Messages;

/**
 * Handles the conversation occurring when a mark done command is entered.
 */
public class MarkDoneConversation extends Conversation {
    private static final String command = "done";
    private String index;

    /**
     * Initialises the Conversation object.
     */

    public MarkDoneConversation() {
        super();
        prompt = Messages.PROMPT_MARKDONE_STARTER;
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
