package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class FindPathConversation extends Conversation {
    private String constraint = "search";
    private String startPointIndex;
    private String endPointIndex;

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            prompt = MessagesPrompt.FINDPATH_PROMPT_STARTER;
            state++;
            break;
        case 1:
            if (input.contains(" ")) {
                prompt = MessagesPrompt.PROMPT_SPACES;
                badAttempt();
            } else if (input.matches("-?\\d+")) {
                prompt = MessagesPrompt.PROMPT_NOT_STRING;
                badAttempt();
            } else {
                prompt = MessagesPrompt.SEARCH_PROMPT_SUCCESS;
                constraint = input;
            }
            break;
        case 2:
            if (input.contains(" ")) {
                prompt = MessagesPrompt.PROMPT_SPACES;
                badAttempt();
            } else {
                prompt = MessagesPrompt.SEARCH_PROMPT_SUCCESS;
                startPointIndex = input;
            }
            break;
        case 3:
            if (input.contains(" ")) {
                prompt = MessagesPrompt.PROMPT_SPACES;
                badAttempt();
            } else {
                prompt = MessagesPrompt.FINDPATH_PROMPT_SUCCESS;
                endPointIndex = input;

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
        result = constraint + " & " + startPointIndex + " & " + endPointIndex;
    }
}
