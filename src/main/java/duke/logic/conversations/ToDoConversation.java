package duke.logic.conversations;

import duke.commons.Messages;

public class ToDoConversation extends Conversation {
    private static final String command = "todo";
    private String description;

    public ToDoConversation() {
        super();
        prompt = Messages.PROMPT_TODO_STARTER;
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
