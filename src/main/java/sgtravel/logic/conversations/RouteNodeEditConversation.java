package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a RouteNodeEdit command is entered.
 */
public class RouteNodeEditConversation extends Conversation {
    private static final String command = "routeNodeEdit";
    private String routeIndex;
    private String nodeIndex;
    private String field;
    private String newValue;

    /**
     * Initialises the Conversation object.
     */
    public RouteNodeEditConversation() {
        super();
        prompt = Messages.PROMPT_ROUTENODE_EDIT_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        switch (state) {
        case 1:
            if (isIntInput(input)) {
                routeIndex = input;
                prompt = Messages.PROMPT_ROUTENODE_EDIT_NODEINDEX;
                state++;
            }

            break;
        case 2:
            if (isIntInput(input)) {
                nodeIndex = input;
                prompt = Messages.PROMPT_ROUTENODE_EDIT_FIELD;
                state++;
            }

            break;
        case 3:
            if (isRouteNodeField(input)) {
                field = input;
                prompt = Messages.PROMPT_ROUTENODE_EDIT_VALUE;
                state++;
            }

            break;
        case 4:
            prompt = Messages.PROMPT_ROUTENODE_EDIT_SUCCESS;
            newValue = input;

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
     * Gets result of prompt.
     *
     * @return result The result.
     */
    @Override
    protected void buildResult() {
        result = command + " " + routeIndex + " " + nodeIndex + " " + field + " " + newValue;
    }
}
