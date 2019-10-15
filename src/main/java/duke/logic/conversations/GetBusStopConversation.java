package duke.logic.conversations;

import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.ParserUtil;

public class GetBusStopConversation extends Conversation {
    private static final String command = "busStop";
    private String busCode;

    public GetBusStopConversation() {
        super();
        prompt = MessagesPrompt.GETBUSROUTE_PROMPT_STARTER;
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
