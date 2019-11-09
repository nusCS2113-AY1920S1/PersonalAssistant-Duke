package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a RouteNodeAddCommand is entered.
 */
public class RouteNodeAddConversation extends Conversation {
    private static final String COMMAND = "routeNodeAdd";
    private String routeIndex;
    private String nodeIndex;
    private String name;
    private String constraint;

    /**
     * Initialises the RouteNodeAddConversation object.
     */
    public RouteNodeAddConversation() {
        super();
        prompt = Messages.PROMPT_ROUTENODE_ADD_STARTER;
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
                prompt = Messages.PROMPT_ROUTENODE_ADD_NODEINDEX;
                state++;
            }

            break;
        case 2:
            if (isIntInput(input)) {
                nodeIndex = input;
                prompt = Messages.PROMPT_ROUTENODE_ADD_TYPE;
                state++;
            }

            break;
        case 3:
            if (isConstraint(input)) {
                constraint = input;
                prompt = Messages.PROMPT_ROUTENODE_ADD_INPUT;
                state++;
            }
            break;
        case 4:
            name = input;
            prompt = Messages.PROMPT_ROUTENODE_ADD_SUCCESS;

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
        if (!"0".equals(nodeIndex)) {
            result = COMMAND + " " + routeIndex + " " + nodeIndex + " at " + name + " by " + constraint;
        } else {
            result = COMMAND + " " + routeIndex + " at " + name + " by " + constraint;
        }
    }
}
