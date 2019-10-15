package duke.logic.conversations;

import duke.commons.MessagesPrompt;

public class ToDoConversation extends Conversation {
    private static final String command = "todo";
    private String description;

    public ToDoConversation() {
        super();
        prompt = MessagesPrompt.TODO_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        description = input;
        buildResult();
    }

    @Override
    protected void buildResult() {
        if (description != null) {
            result = command + " " + description;
            setFinished(true);
        } else {
            attempts++;
        }
    }
}
