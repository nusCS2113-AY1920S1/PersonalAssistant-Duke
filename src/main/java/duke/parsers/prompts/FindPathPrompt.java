package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class FindPathPrompt extends Prompt {
    private String constraint = "search";
    private String startPointIndex;
    private String endPointIndex;

    public FindPathPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            message = PromptMessages.FINDPATH_PROMPT_STARTER;
            state++;
            break;
        case 1:
            if (input.contains(" ")) {
                message = PromptMessages.PROMPT_SPACES;
                badAttempt();
            } else if (input.matches("-?\\d+")) {
                message = PromptMessages.PROMPT_NOT_STRING;
                badAttempt();
            } else {
                message = PromptMessages.SEARCH_PROMPT_SUCCESS;
                constraint = input;
            }
            break;
        case 2:
            if (input.contains(" ")) {
                message = PromptMessages.PROMPT_SPACES;
                badAttempt();
            } else {
                message = PromptMessages.SEARCH_PROMPT_SUCCESS;
                startPointIndex = input;
            }
            break;
        case 3:
            if (input.contains(" ")) {
                message = PromptMessages.PROMPT_SPACES;
                badAttempt();
            } else {
                message = PromptMessages.FINDPATH_PROMPT_SUCCESS;
                endPointIndex = input;

                buildResult();
                isDone = true;
            }
            break;
        default:
            message = PromptMessages.PROMPT_ERROR;
            break;
        }
        if (attempts > 4) {
            message = PromptMessages.PROMPT_TOO_MANY_ATTEMPTS;
            isDone = true;
            isCancelled = true;
        }
    }

    @Override
    void buildResult() {
        result = constraint + " & " + startPointIndex + " & " + endPointIndex;
    }
}
