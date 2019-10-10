package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class FindPathPrompt extends Prompt {
    private String constraint = "search";
    private String startPointIndex;
    private String endPointIndex;

    public FindPathPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch(state) {
            case 0:
                ui.show(PromptMessages.FINDPATH_PROMPT_STARTER);
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
                    ui.show(PromptMessages.SEARCH_PROMPT_SUCCESS);
                    constraint = input;
                }
                break;
            case 2:
                if (input.contains(" ")) {
                    ui.show(PromptMessages.PROMPT_SPACES);
                    badAttempt();
                } else {
                    ui.show(PromptMessages.SEARCH_PROMPT_SUCCESS);
                    startPointIndex = input;
                }
                break;
            case 3:
                if (input.contains(" ")) {
                    ui.show(PromptMessages.PROMPT_SPACES);
                    badAttempt();
                } else {
                    ui.show(PromptMessages.FINDPATH_PROMPT_SUCCESS);
                    endPointIndex = input;

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
        result = constraint + " & " + startPointIndex + " & " + endPointIndex;
    }
}
