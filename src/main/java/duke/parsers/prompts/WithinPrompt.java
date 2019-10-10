package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class WithinPrompt extends Prompt {
    private String command = "within";
    private String description;
    private String endDate;

    public WithinPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            message = PromptMessages.WITHIN_PROMPT_STARTER;
            state++;
            break;
        case 1:
            message = PromptMessages.WITHIN_PROMPT_ENDDATE;
            description = input;
            state++;
            break;
        case 2:
            message = PromptMessages.WITHIN_PROMPT_SUCCESS;
            endDate = input;

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
        result = command + " & " + description + " & " + endDate;
    }
}
