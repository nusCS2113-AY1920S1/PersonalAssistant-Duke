package duke.logic.conversations;

import duke.commons.Messages;

public class SearchConversation extends Conversation {
    private static final String command = "search";
    private String location;

    public SearchConversation() {
        super();
        prompt = Messages.PROMPT_SEARCH_STARTER;
    }

    @Override
    public void execute(String input) {
        prompt = Messages.PROMPT_SEARCH_SUCCESS;
        location = input.replace(" ", "+");
        buildResult();
        setFinished(true);
    }

    @Override
    protected void buildResult() {
        if (location != null) {
            result = command + " " + location;
            setFinished(true);
        } else {
            attempts++;
        }
    }
}
