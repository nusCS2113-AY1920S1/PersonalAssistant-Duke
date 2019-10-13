package duke.logic.conversations;

import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.ParserUtil;

public class FreeTimeConversation extends Conversation {
    private static final String command = "findtime";
    private String duration;

    public FreeTimeConversation() {
        super();
        prompt = MessagesPrompt.FREETIME_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        try {
            ParserUtil.getIndex(input);
            duration = input;
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
        result = command + " " + duration;
    }
}
