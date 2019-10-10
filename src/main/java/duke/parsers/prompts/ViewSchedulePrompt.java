package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class ViewSchedulePrompt extends Prompt {
    private String command = "fetch";
    private String date;

    public ViewSchedulePrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch(state) {
            case 0:
                ui.show(PromptMessages.VIEWSCHEDULE_PROMPT_STARTER);
                state++;
                break;
            case 1:
                if (input.contains(" ")) {
                    ui.show(PromptMessages.PROMPT_SPACES);
                    badAttempt();
                } else {
                    ui.show(PromptMessages.VIEWSCHEDULE_PROMPT_SUCCESS);
                    date = input;

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
        result = command + " & " + date;
    }
}
