package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a RouteList command is entered.
 */
public class RouteListConversation extends Conversation {
    private static final String command = "routeList";
    private String index;

    /**
     * Initialises the RouteListConversation object.
     */
    public RouteListConversation() {
        super();
        prompt = Messages.PROMPT_ROUTE_LIST_STARTER;
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
        }

        tryCancelConversation(input);
    }

    /**
     * Builds the result of the conversation string.
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
