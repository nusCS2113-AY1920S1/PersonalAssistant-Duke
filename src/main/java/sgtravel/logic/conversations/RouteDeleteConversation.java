package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a RouteDelete command is entered.
 */
public class RouteDeleteConversation extends Conversation {
    private static final String command = "routeDelete";
    private String index;

    /**
     * Initialises the RouteDeleteConversation object.
     */
    public RouteDeleteConversation() {
        super();
        prompt = Messages.PROMPT_ROUTE_DELETE_STARTER;
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
            prompt = Messages.PROMPT_ROUTE_DELETE_SUCCESS;
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
