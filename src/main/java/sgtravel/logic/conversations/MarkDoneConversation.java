package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a mark done command is entered.
 */
public class MarkDoneConversation extends Conversation {
    private static final String command = "done";
    private String index;

    /**
     * Initialises the MarkDoneConversation object.
     */
    public MarkDoneConversation() {
        super();
        prompt = Messages.PROMPT_MARKDONE_STARTER;
    }


    /**
     * Executes Prompt and returns a String reply.
     *
     * @param input The user input.
     */
    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            index = input;
            buildResult();
            setFinished(true);
        }

        tryCancelConversation(input);
    }

    /**
     * Builds the result of the conversation string.
     */
    @Override
    protected void buildResult() {
        assert (index != null);
        result = command + " " + index;
    }
}
