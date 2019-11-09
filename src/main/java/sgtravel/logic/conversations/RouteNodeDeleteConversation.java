package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a RouteNodeDelete command is entered.
 */
public class RouteNodeDeleteConversation extends Conversation {
    private static final String COMMAND = "routeNodeDelete";
    private String routeIndex;
    private String nodeIndex;

    /**
     * Initialises the RouteNodeDeleteConversation object.
     */
    public RouteNodeDeleteConversation() {
        super();
        prompt = Messages.PROMPT_ROUTENODE_DELETE_STARTER;
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
            if (isIntInput(input)) {
                routeIndex = input;
                prompt = Messages.PROMPT_ROUTENODE_DELETE_NODEINDEX;
                state++;
            }

            break;
        case 2:
            if (isIntInput(input)) {
                nodeIndex = input;
                prompt = Messages.PROMPT_ROUTENODE_DELETE_SUCCESS;
                buildResult();
                setFinished(true);
            }

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
        result = COMMAND + " " + routeIndex + " " + nodeIndex;
    }
}
