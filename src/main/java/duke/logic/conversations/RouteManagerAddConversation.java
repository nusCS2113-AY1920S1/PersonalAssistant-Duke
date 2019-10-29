package duke.logic.conversations;

import duke.commons.Messages;
import duke.logic.RouteManager;

/**
 * Handles the conversation occurring when an Add command is entered with the RouteManager.
 */
public class RouteManagerAddConversation extends Conversation {
    private boolean isAddAtEnd;
    private static final String COMMAND = "routeNodeAdd";
    private String routeIndex;
    private String nodeIndex;
    private String type;
    private String address;

    /**
     * Initialises the Conversation object.
     */
    public RouteManagerAddConversation(RouteManager routeManager) {
        super();
        prompt = Messages.PROMPT_ROUTENODE_ADD_TYPE;
        if (routeManager.getNodeIndex() == -1) {
            isAddAtEnd = true;
            routeIndex = String.valueOf(routeManager.getRouteIndex());
        } else {
            isAddAtEnd = false;
            routeIndex = String.valueOf(routeManager.getRouteIndex());
            nodeIndex = String.valueOf(routeManager.getNodeIndex());
        }
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        switch (state) {
            case 1:
                if (isConstraint(input)) {
                    type = input;
                    prompt = Messages.PROMPT_ROUTENODE_ADD_INPUT;
                    state++;
                }

                break;
            case 2:
                address = input;

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
        if (isAddAtEnd) {
            result = COMMAND + " " + routeIndex + " at " + address + " by " + type;
        } else {
            result = COMMAND + " " + routeIndex + " at " + nodeIndex + " " + address + " by " + type;
        }
    }
}
