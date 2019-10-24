package duke.logic.conversations;

import duke.commons.Messages;

public class DeleteConversation extends Conversation {
    private static final String command = "delete";
    private String index;

    public DeleteConversation() {
        super();
        prompt = Messages.PROMPT_DELETE_STARTER;
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
