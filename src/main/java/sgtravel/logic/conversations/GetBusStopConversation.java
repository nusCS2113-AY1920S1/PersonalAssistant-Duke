package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

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
        prompt = Messages.PROMPT_GETBUSSTOP_STARTER;
    }

    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            busCode = input;
            buildResult();
            setFinished(true);
        }

        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        result = command + " " + busCode;
    }
}
