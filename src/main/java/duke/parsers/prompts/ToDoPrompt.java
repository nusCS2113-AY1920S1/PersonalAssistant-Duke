package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class ToDoPrompt extends Prompt {
    private String command = "todo";
    private String description;

    public ToDoPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        System.out.println("executing todo");
        switch (state) {
        case 0:
            message = PromptMessages.TODO_PROMPT_STARTER;
            state++;
            break;
        case 1:
            description = input;
            message = PromptMessages.TODO_PROMPT_SUCCESS;
            state++;
            buildResult();
            isDone = true;
            break;
        default:
            message = PromptMessages.PROMPT_ERROR;
            break;
        }
        if (attempts > 4) {
            isDone = true;
            isCancelled = true;
        }
    }

    @Override
    void buildResult() {
        result = command + " & " + description;
    }
}
