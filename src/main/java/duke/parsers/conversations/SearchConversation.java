package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class SearchConversation extends Conversation {
    private String command = "search";
    private String location;

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            prompt = MessagesPrompt.SEARCH_PROMPT_STARTER;
            state++;
            break;
        case 1:
            if (input.contains(" ")) {
                prompt = MessagesPrompt.PROMPT_SPACES;
                badAttempt();
            } else {
                prompt = MessagesPrompt.SEARCH_PROMPT_SUCCESS;
                location = input;

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
        result = command + " & " + location;
    }
}
