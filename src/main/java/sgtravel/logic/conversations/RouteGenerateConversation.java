package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a RouteGenerate command is entered.
 */
public class RouteGenerateConversation extends Conversation {
    private static final String COMMAND = "routeGenerate";
    private String startLocation;
    private String endLocation;
    private String constraint;

    /**
     * Initialises the RouteGenerateConversation object.
     */
    public RouteGenerateConversation() {
        super();
        prompt = Messages.PROMPT_ROUTE_GENERATE_STARTER;
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
            startLocation = input;
            prompt = Messages.PROMPT_ROUTE_GENERATE_ENDLOCATION;

            state++;
            break;
        case 2:
            endLocation = input;
            prompt = Messages.PROMPT_ROUTE_GENERATE_CONSTRAINT;

            state++;
            break;
        case 3:
            if (isConstraint(input)) {
                constraint = input;
                prompt = Messages.PROMPT_ROUTE_GENERATE_SUCCESS;
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
        result = COMMAND + " " + startLocation + " to " + endLocation + " by " + constraint;
    }
}
