package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a RouteAdd command is entered.
 */
public class RouteAddConversation extends Conversation {
    private static final String command = "routeAdd";
    private String name;
    private String description;

    /**
     * Initialises the RouteAddConversation object.
     */
    public RouteAddConversation() {
        super();
        prompt = Messages.PROMPT_ROUTE_ADD_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     *
     * @param input The user input.
     */
    @Override
    public void execute(String input) {
        switch (state) {
        case 1:
            prompt = Messages.PROMPT_ROUTE_ADD_DESCRIPTION;
            name = input;
            state++;
            break;
        case 2:
            prompt = Messages.PROMPT_ROUTE_ADD_SUCCESS;
            description = input;

            buildResult();
            setFinished(true);
            break;
        default:
            prompt = Messages.PROMPT_ERROR;
            break;
        }
        tryCancelConversation(input);
    }

    /**
     * Builds the result of the conversation string.
     */
    @Override
    protected void buildResult() {
        result = command + " " + name + " desc " + description;
    }
}
