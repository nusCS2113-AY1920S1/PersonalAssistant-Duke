package duke.logic.conversations;

import duke.commons.MessagesPrompt;

/**
 * Handles the conversation occurring when a findTime command is entered.
 */
public class FreeTimeConversation extends Conversation {
    private static final String command = "findtime";
    private String duration;

    public FreeTimeConversation() {
        super();
        prompt = MessagesPrompt.FREETIME_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        if (isIntInput(input)) {
            duration = input;
            buildResult();
        }

        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        if (duration != null) {
            result = command + " " + duration;
        } else {
            attempts++;
        }
    }
}
