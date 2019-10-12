package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class RecommendationsConversation extends Conversation {
    private String command = "recommend";
    private String days;

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            prompt = MessagesPrompt.RECOMMEND_PROMPT_STARTER;
            state++;
            break;
        case 1:
            prompt = MessagesPrompt.RECOMMEND_PROMPT_SUCCESS;
            days = input;
            buildResult();
            isDone = true;
            break;
        default:
            prompt = MessagesPrompt.PROMPT_ERROR;
            break;
        }
        if (attempts > 4) {
            prompt = MessagesPrompt.PROMPT_TOO_MANY_ATTEMPTS;
            isDone = true;
            isCancelled = true;
        }
    }

    @Override
    void buildResult() { result = command + " & " + days ; }
}
