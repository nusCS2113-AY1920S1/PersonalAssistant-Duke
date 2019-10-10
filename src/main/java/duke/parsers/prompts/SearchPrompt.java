package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class SearchPrompt extends Prompt {
    private String command = "search";
    private String location;

    public SearchPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch(state) {
            case 0:
                ui.show(PromptMessages.SEARCH_PROMPT_STARTER);
                state++;
                break;
            case 1:
                if (input.contains(" ")) {
                    ui.show(PromptMessages.PROMPT_SPACES);
                    badAttempt();
                } else {
                    ui.show(PromptMessages.SEARCH_PROMPT_SUCCESS);
                    location = input;

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
        result = command + " & " + location;
    }
}
