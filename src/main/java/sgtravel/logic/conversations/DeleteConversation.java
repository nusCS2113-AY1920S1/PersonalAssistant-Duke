package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a delete command is entered.
 */
public class DeleteConversation extends Conversation {
    private static final String command = "delete";
    private String index;

    /**
     * Initialises the Conversation object.
     */
    public DeleteConversation() {
        super();
        prompt = Messages.PROMPT_DELETE_STARTER;
    }

    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            index = input;
            buildResult();
            setFinished(true);
        }

        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        assert (index != null);
        result = command + " " + index;
    }
}
