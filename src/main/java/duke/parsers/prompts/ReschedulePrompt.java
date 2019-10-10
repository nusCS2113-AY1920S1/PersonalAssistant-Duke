package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class ReschedulePrompt extends Prompt {
    private String command = "reschedule";
    private String index;
    private String date;

    public ReschedulePrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            message = PromptMessages.RESCHEDULE_PROMPT_STARTER;
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
                message = PromptMessages.RESCHEDULE_PROMPT_SUCCESS;
                index = input;
            }
            break;
        case 2:
            if (input.contains(" ")) {
                message = PromptMessages.PROMPT_SPACES;
                badAttempt();
            } else {
                message = PromptMessages.RESCHEDULE_PROMPT_SUCCESS;
                date = input;

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
        result = command + " & " + index + " & " + date;
    }
}
