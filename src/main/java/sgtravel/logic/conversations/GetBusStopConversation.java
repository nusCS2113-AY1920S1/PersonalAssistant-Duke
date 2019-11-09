package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a busStop command is entered.
 */
public class GetBusStopConversation extends Conversation {
    private static final String COMMAND = "busStop";
    private String busCode;

    /**
     * Initialises the GetBusStopConversation object.
     */
    public GetBusStopConversation() {
        super();
        prompt = Messages.PROMPT_GETBUSSTOP_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     *
     * @param input The user input.
     */
    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            busCode = input;
            buildResult();
            setFinished(true);
        }

        tryCancelConversation(input);
    }

    /**
     * Builds the result of the conversation string.
     */
    @Override
    protected void buildResult() {
        result = COMMAND + " " + busCode;
    }
}
