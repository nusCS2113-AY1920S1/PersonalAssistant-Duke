package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class FreeTimePrompt extends Prompt {
    private String command = "findtime";
    private String duration;

    public FreeTimePrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            message = PromptMessages.FREETIME_PROMPT_STARTER;
            state++;
            break;
        case 1:
            if (input.contains(" ")) {
                message = PromptMessages.PROMPT_SPACES;
                badAttempt();
            } else if (!input.matches("-?\\d+")) {
                message = PromptMessages.PROMPT_NOT_INT;
                badAttempt();
            } else {
                message = PromptMessages.FREETIME_PROMPT_SUCCESS;
                duration = input;

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
        result = command + " & " + duration;
    }
}
