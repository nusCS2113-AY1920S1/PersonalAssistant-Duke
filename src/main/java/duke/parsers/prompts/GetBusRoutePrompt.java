package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class GetBusRoutePrompt extends Prompt {
    private String command = "busRoute";
    private String bus;

    public GetBusRoutePrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            message = PromptMessages.GETBUSROUTE_PROMPT_STARTER;
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
                message = PromptMessages.GETBUSROUTE_PROMPT_SUCCESS;
                bus = input;

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
        result = command + " & " + bus;
    }
}
