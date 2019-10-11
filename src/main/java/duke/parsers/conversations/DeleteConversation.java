package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class DeleteConversation extends Conversation {
    private String command = "delete";
    private String index;

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            prompt = MessagesPrompt.DELETE_PROMPT_STARTER;
            state++;
            break;
        case 1:
            if (!input.matches("-?\\d+")) {
                prompt = MessagesPrompt.PROMPT_NOT_INT;
                badAttempt();
            } else if (input.contains(" ")) {
                prompt = MessagesPrompt.PROMPT_SPACES;
                badAttempt();
            } else {
                prompt = MessagesPrompt.DELETE_PROMPT_SUCCESS;
                index = input;

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
        result = command + " & " + index;
    }
}
