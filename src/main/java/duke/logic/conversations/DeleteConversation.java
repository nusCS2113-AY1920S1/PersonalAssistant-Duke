package duke.logic.conversations;

import duke.commons.MessagesPrompt;

public class DeleteConversation extends Conversation {
    private static final String command = "delete";
    private String index;

    public DeleteConversation() {
        super();
        prompt = MessagesPrompt.DELETE_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            index = input;
            buildResult();
        }

        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        if (index != null) {
            result = command + " " + index;
            setFinished(true);
        } else {
            attempts++;
        }
    }
}
