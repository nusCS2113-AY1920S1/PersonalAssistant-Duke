package duke.logic.conversations;

import duke.commons.Messages;

/**
 * Handles the conversation occurring when a findPath command is entered.
 */
public class FindPathConversation extends Conversation {
    private static final String command = "findPath";
    private String constraint;
    private String startPointIndex;
    private String endPointIndex;

    /**
     * Initialises the Conversation object.
     */

    public FindPathConversation() {
        super();
        prompt = Messages.PROMPT_FIND_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        switch (state) {
        case 1:
            prompt = Messages.PROMPT_ROUTE_STARTING_POINT;
            constraint = input;
            state++;
            break;
        case 2:
            if (isIntInput(input)) {
                startPointIndex = input;
                prompt = Messages.PROMPT_ROUTE_ENDING_POINT;
                state++;
                attempts = 0;
            }
            break;
        case 3:
            if (isIntInput(input)) {
                endPointIndex = input;
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
        if (constraint != null && startPointIndex != null && endPointIndex != null) {
            result = command + " " + constraint + " " + startPointIndex + " " + endPointIndex;
        } else {
            attempts++;
        }
    }
}
