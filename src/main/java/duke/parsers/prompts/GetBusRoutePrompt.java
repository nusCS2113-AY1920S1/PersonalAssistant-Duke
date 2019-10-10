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
        switch(state) {
            case 0:
                ui.show(PromptMessages.GETBUSROUTE_PROMPT_STARTER);
                state++;
                break;
            case 1:
                if (input.contains(" ")) {
                    ui.show(PromptMessages.PROMPT_SPACES);
                    badAttempt();
                } else if (input.matches("-?\\d+")) {
                    ui.show(PromptMessages.PROMPT_NOT_STRING);
                    badAttempt();
                } else {
                    ui.show(PromptMessages.GETBUSROUTE_PROMPT_SUCCESS);
                    bus = input;

                    buildResult();
                    isDone = true;
                }
                break;
        }
        if (attempts > 4) {
            ui.show(PromptMessages.PROMPT_TOO_MANY_ATTEMPTS);
            isDone = true;
            isCancelled = true;
        }
    }

    @Override
    void buildResult() {
        result = command + " & " + bus;
    }
}
