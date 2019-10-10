package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class RepeatPrompt extends Prompt {
    private String command = "repeat";
    private String description;
    private String date;
    private String repeatDays;

    public RepeatPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch(state) {
            case 0:
                ui.show(PromptMessages.REPEAT_PROMPT_STARTER);
                state++;
                break;
            case 1:
                ui.show(PromptMessages.REPEAT_PROMPT_DATE);
                description = input;
                state++;

                break;
            case 2:
                ui.show(PromptMessages.REPEAT_PROMPT_REPEAT);
                date = input;
                state++;
                break;
            case 3:
                ui.show(PromptMessages.REPEAT_PROMPT_SUCCESS);
                repeatDays = input;

                buildResult();
                isDone = true;
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
        result = command + " & " + description + " & " + date + " & " + repeatDays;
    }
}
