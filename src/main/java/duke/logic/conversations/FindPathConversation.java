package duke.logic.conversations;

import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.ParserUtil;

public class FindPathConversation extends Conversation {
    private static final String command = "findPath";
    private String constraint;
    private String startPointIndex;
    private String endPointIndex;

    public FindPathConversation() {
        super();
        prompt = MessagesPrompt.FINDPATH_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        switch (state) {
        case 1:
            prompt = MessagesPrompt.STARTING_POINT_PROMPT;
            constraint = input;
            state++;
            break;
        case 2:
            try {
                ParserUtil.getIndex(input);
                prompt = MessagesPrompt.ENDPOINT_PROMPT;
                startPointIndex = input;
                state++;
                attempts = 0;
            } catch (DukeException e) {
                attempts++;
                prompt = MessagesPrompt.PROMPT_NOT_INT;
            }
            break;
        case 3:
            try {
                ParserUtil.getIndex(input);
                endPointIndex = input;
                buildResult();
                setFinished(true);
            } catch (DukeException e) {
                attempts++;
                prompt = MessagesPrompt.PROMPT_NOT_INT;
            }
            break;
        default:
            prompt = MessagesPrompt.PROMPT_ERROR;
            break;
        }
        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        result = command + " " + constraint + " " + startPointIndex + " " + endPointIndex;
    }
}
