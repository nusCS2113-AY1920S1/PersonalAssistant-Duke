package duke.logic.conversations;

import duke.commons.Messages;

/**
 * Handles the conversation occurring when RouteManager is active, to select the node to use.
 */
public class RouteManagerNodeSelectConversation extends Conversation {
    private static final String command = "routeManagerNodeSelect";
    private String index;

    /**
     * Initialises the Conversation object.
     */
    public RouteManagerNodeSelectConversation() {
        super();
        prompt = Messages.PROMPT_ROUTE_SELECTOR_NODESELECT_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            index = input;
            buildResult();
            setFinished(true);
        }
    }

    /**
     * Gets result of prompt.
     *
     * @return result The result.
     */
    @Override
    protected void buildResult() {
        result = command + " " + index;
    }
}
