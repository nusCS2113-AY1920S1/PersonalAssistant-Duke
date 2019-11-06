package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a search command is entered.
 */
public class SearchConversation extends Conversation {
    private static final String command = "search";
    private String location;

    /**
     * Initialises the Conversation object.
     */
    public SearchConversation() {
        super();
        prompt = Messages.PROMPT_SEARCH_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        prompt = Messages.PROMPT_SEARCH_SUCCESS;
        location = input.replace(" ", "+");
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
        if (location != null) {
            result = command + " " + location;
            setFinished(true);
        } else {
            attempts++;
        }
    }
}
