package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a find command is entered.
 */
public class FindConversation extends Conversation {
    private static final String command = "find";
    private String keyword;

    /**
     * Initialises the Conversation object.
     */
    public FindConversation() {
        super();
        prompt = Messages.PROMPT_FIND_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        keyword = input;
        buildResult();
        setFinished(true);
    }

    /**
     * Gets result of prompt.
     *
     * @return result The result.
     */
    @Override
    protected void buildResult() {
        assert  (keyword != null);
        result = command + " " + keyword;
    }
}
