package duke.logic.conversations;

import duke.commons.Messages;

public class GetBusStopConversation extends Conversation {
    private static final String command = "busStop";
    private String busCode;

    public GetBusStopConversation() {
        super();
        prompt = Messages.PROMPT_GETBUSROUTE_STARTER;
    }

    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            busCode = input;
            buildResult();
        }

        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        if (busCode != null) {
            result = command + " " + busCode;
        } else {
            attempts++;
        }
    }
}
