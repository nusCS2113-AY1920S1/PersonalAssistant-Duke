package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class FixedPrompt extends Prompt {
    private String command = "fixed";
    private String description;
    private String hour;
    private String min;

    public FixedPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            message = PromptMessages.FIXED_PROMPT_STARTER;
            state++;
            break;
        case 1:
            message = PromptMessages.FIXED_PROMPT_HOUR;
            description = input;
            state++;
            break;
        case 2:
            message = PromptMessages.FIXED_PROMPT_MIN;
            hour = input;
            break;
        case 3:
            message = PromptMessages.FIXED_PROMPT_SUCCESS;
            min = input;

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
        result = command + " & " + description + " & " + hour + " & " + min;
    }
}
