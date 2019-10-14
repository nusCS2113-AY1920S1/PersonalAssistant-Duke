package duke.logic.conversations;

import duke.commons.MessagesPrompt;

public class FindConversation extends Conversation {
    private static final String command = "find";
    private String keyword;

    public FindConversation() {
        super();
        prompt = MessagesPrompt.FIND_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        keyword = input;
        buildResult();
        setFinished(true);
    }

    @Override
    protected void buildResult() {
        result = command + " " + keyword;
    }
}
