package duke.logic.conversations;

import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.ParserUtil;

public class MarkDoneConversation extends Conversation {
    private static final String command = "done";
    private String index;

    public MarkDoneConversation() {
        super();
        prompt = MessagesPrompt.MARKDONE_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        try {
            ParserUtil.getIndex(input);
            index = input;
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
        result = command + " " + index;
    }
}
