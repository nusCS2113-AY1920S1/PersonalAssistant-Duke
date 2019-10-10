package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class GetBusStopConversation extends Conversation {
    private String command = "busStop";
    private String buscode;

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            prompt = MessagesPrompt.GETBUSSTOP_PROMPT_STARTER;
            state++;
            break;
        case 1:
            if (input.contains(" ")) {
                prompt = MessagesPrompt.PROMPT_SPACES;
                badAttempt();
            } else if (!input.matches("-?\\d+")) {
                prompt = MessagesPrompt.PROMPT_NOT_INT;
                badAttempt();
            } else {
                prompt = MessagesPrompt.GETBUSSTOP_PROMPT_SUCCESS;
                buscode = input;

                buildResult();
                isDone = true;
            }
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
        result = command + " | " + buscode;
    }
}
