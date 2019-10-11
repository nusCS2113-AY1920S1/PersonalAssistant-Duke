package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class ToDoConversation extends Conversation {
    private String command = "todo";
    private String description;

    @Override
    public void execute(String input, Ui ui) {
        System.out.println("executing todo");
        switch (state) {
        case 0:
            prompt = MessagesPrompt.TODO_PROMPT_STARTER;
            state++;
            break;
        case 1:
            description = input;
            prompt = MessagesPrompt.TODO_PROMPT_SUCCESS;
            state++;
            buildResult();
            isDone = true;
            break;
        default:
            prompt = MessagesPrompt.PROMPT_ERROR;
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
