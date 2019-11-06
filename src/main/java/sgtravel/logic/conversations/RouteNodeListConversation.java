package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a RouteNodeList command is entered.
 */
public class RouteNodeListConversation extends Conversation {
    private static final String command = "routeNodeList";
    private String routeIndex;
    private String nodeIndex;

    /**
     * Initialises the Conversation object.
     */
    public RouteNodeListConversation() {
        super();
        prompt = Messages.PROMPT_ROUTENODE_SHOW_STARTER;
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
                prompt = Messages.PROMPT_ROUTENODE_SHOW_NODEINDEX;
                state++;
            }

            break;
        case 2:
            if (isIntInput(input)) {
                nodeIndex = input;
                prompt = Messages.PROMPT_ROUTENODE_SHOW_SUCCESS;
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
     * Gets result of prompt.
     *
     * @return result The result.
     */
    @Override
    protected void buildResult() {
        result = command + " " + routeIndex + " " + nodeIndex;
    }
}
