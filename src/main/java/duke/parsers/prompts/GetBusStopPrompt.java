package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class GetBusStopPrompt extends Prompt {
    private String command = "busStop";
    private String buscode;

    public GetBusStopPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch(state) {
            case 0:
                ui.show(PromptMessages.GETBUSSTOP_PROMPT_STARTER);
                state++;
                break;
            case 1:
                if (input.contains(" ")) {
                    ui.show(PromptMessages.PROMPT_SPACES);
                    badAttempt();
                } else if (!input.matches("-?\\d+")) {
                    ui.show(PromptMessages.PROMPT_NOT_INT);
                    badAttempt();
                } else {
                    ui.show(PromptMessages.GETBUSSTOP_PROMPT_SUCCESS);
                    buscode = input;

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
        result = command + " | " + buscode;
    }
}
