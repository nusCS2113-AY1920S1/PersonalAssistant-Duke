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
        try {
            ParserUtil.getIndex(input);
            busCode = input;
            buildResult();
            setFinished(true);
        } catch (DukeException e) {
            attempts++;
            prompt = MessagesPrompt.PROMPT_NOT_INT;
        }
        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        result = command + " " + busCode;
    }
}
