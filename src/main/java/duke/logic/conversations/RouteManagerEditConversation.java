package duke.logic.conversations;

import duke.commons.Messages;
import duke.logic.RouteManager;

/**
 * Handles the conversation occurring when an Edit command is entered with the RouteManager.
 */
public class RouteManagerEditConversation extends Conversation {
    private boolean isRouteEdit;
    private String command;
    private String routeIndex;
    private String nodeIndex;
    private String field;
    private String newValue;

    /**
     * Initialises the Conversation object.
     */
    public RouteManagerEditConversation(RouteManager routeManager) {
        super();
        if (routeManager.getNodeIndex() == -1) {
            isRouteEdit = true;
            prompt = Messages.PROMPT_ROUTE_EDIT_FIELD;
            routeIndex = String.valueOf(routeManager.getRouteIndex());
            command = "routeEdit";
        } else {
            isRouteEdit = false;
            prompt = Messages.PROMPT_ROUTENODE_EDIT_FIELD;
            routeIndex = String.valueOf(routeManager.getRouteIndex());
            nodeIndex = String.valueOf(routeManager.getNodeIndex());
            command = "routeNodeEdit";
        }
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        switch (state) {
        case 1:
            if (isRouteEdit && isRouteField(input)) {
                field = input;
                prompt = Messages.PROMPT_ROUTENODE_EDIT_VALUE;
                state++;
            } else if (!isRouteEdit && isRouteNodeField(input)) {
                field = input;
                prompt = Messages.PROMPT_ROUTENODE_EDIT_VALUE;
                state++;
            }

            break;
        case 2:
            if (isRouteEdit) {
                prompt = Messages.PROMPT_ROUTE_EDIT_SUCCESS;
            } else {
                prompt = Messages.PROMPT_ROUTENODE_EDIT_SUCCESS;
            }
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
        if (isRouteEdit) {
            result = command + " " + routeIndex + " " + field + " " + newValue;
        } else {
            result = command + " " + routeIndex + " " + nodeIndex + " " + field + " " + newValue;
        }
    }
}
