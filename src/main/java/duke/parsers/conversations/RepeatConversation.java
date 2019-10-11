package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class RepeatConversation extends Conversation {
    private String command = "repeat";
    private String description;
    private String date;
    private String repeatDays;

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            prompt = MessagesPrompt.REPEAT_PROMPT_STARTER;
            state++;
            break;
        case 1:
            prompt = MessagesPrompt.REPEAT_PROMPT_DATE;
            description = input;
            state++;
            break;
        case 2:
            prompt = MessagesPrompt.REPEAT_PROMPT_REPEAT;
            date = input;
            state++;
            break;
        case 3:
            prompt = MessagesPrompt.REPEAT_PROMPT_SUCCESS;
            repeatDays = input;

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
        result = command + " & " + description + " & " + date + " & " + repeatDays;
    }
}
