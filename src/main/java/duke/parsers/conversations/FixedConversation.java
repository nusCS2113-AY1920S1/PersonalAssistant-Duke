package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class FixedConversation extends Conversation {
    private String command = "fixed";
    private String description;
    private String hour;
    private String min;

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            prompt = MessagesPrompt.FIXED_PROMPT_STARTER;
            state++;
            break;
        case 1:
            prompt = MessagesPrompt.FIXED_PROMPT_HOUR;
            description = input;
            state++;
            break;
        case 2:
            prompt = MessagesPrompt.FIXED_PROMPT_MIN;
            hour = input;
            break;
        case 3:
            prompt = MessagesPrompt.FIXED_PROMPT_SUCCESS;
            min = input;

            buildResult();
            isDone = true;
            break;
        default:
            prompt = MessagesPrompt.PROMPT_ERROR;
            break;
        }
        if (attempts > 4) {
            prompt = MessagesPrompt.PROMPT_TOO_MANY_ATTEMPTS;
            isDone = true;
            isCancelled = true;
        }
    }

    @Override
    void buildResult() {
        result = command + " & " + description + " & " + hour + " & " + min;
    }
}
