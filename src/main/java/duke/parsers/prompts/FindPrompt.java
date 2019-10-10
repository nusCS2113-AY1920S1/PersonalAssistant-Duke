package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class FindPrompt extends Prompt {
    private String command = "find";
    private String keyword;

    public FindPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            message = PromptMessages.FIND_PROMPT_STARTER;
            state++;
            break;
        case 1:
            message = PromptMessages.FIND_PROMPT_SUCCESS;
            keyword = input;

            buildResult();
            isDone = true;
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
        result = command + " & " + keyword;
    }
}
