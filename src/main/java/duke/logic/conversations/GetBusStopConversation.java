package duke.logic.conversations;

import duke.commons.Messages;

/**
 * Handles the conversation occurring when a busStop command is entered.
 */
public class GetBusStopConversation extends Conversation {
    private static final String command = "busStop";
    private String busCode;

    /**
     * Initialises the Conversation object.
     */

    public GetBusStopConversation() {
        super();
        prompt = Messages.PROMPT_GETBUSROUTE_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     */
    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            busCode = input;
            buildResult();
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
        if (busCode != null) {
            result = command + " " + busCode;
        } else {
            attempts++;
        }
    }
}
