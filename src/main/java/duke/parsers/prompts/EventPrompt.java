package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class EventPrompt extends Prompt {
    private String command = "event";
    private String description;
    private String date;

    public EventPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch(state) {
            case 0:
                ui.show(PromptMessages.EVENT_PROMPT_STARTER);
                state++;
                break;
            case 1:
                ui.show(PromptMessages.EVENT_PROMPT_DATE);
                description = input;
                state++;
                break;
            case 2:
                ui.show(PromptMessages.EVENT_PROMPT_SUCCESS);
                date = input;

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
        result = command + " & " + description + " & " + date;
    }
}
