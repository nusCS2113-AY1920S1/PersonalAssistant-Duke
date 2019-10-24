package duke.logic.conversations;

import duke.commons.Messages;

public class FreeTimeConversation extends Conversation {
    private static final String command = "findtime";
    private String duration;

    public FreeTimeConversation() {
        super();
        prompt = Messages.PROMPT_FREETIME_STARTER;
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
