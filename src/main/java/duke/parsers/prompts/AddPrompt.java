package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class AddPrompt extends Prompt {
    private String command = "add";
    private String description;

    public AddPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch(state) {
            case 0:
                ui.show(PromptMessages.ADD_PROMPT_STARTER);
                state++;
                break;
            case 1:
                ui.show(PromptMessages.ADD_PROMPT_SUCCESS);
                description = input;

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
        result = command + " & " + description;
    }
}
