package duke.logic.conversations;

import duke.commons.MessagesPrompt;

/**
 * Class which handles the conversation occurring when a findPath command is entered.
 */
public class FindPathConversation extends Conversation {
    private static final String command = "findPath";
    private String constraint;
    private String startPointIndex;
    private String endPointIndex;

    public FindPathConversation() {
        super();
        prompt = MessagesPrompt.FINDPATH_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        switch (state) {
        case 1:
            prompt = MessagesPrompt.STARTING_POINT_PROMPT;
            constraint = input;
            state++;
            break;
        case 2:
            if (isIntInput(input)) {
                startPointIndex = input;
                prompt = MessagesPrompt.ENDPOINT_PROMPT;
                state++;
                attempts = 0;
            }
            break;
        case 3:
            if (isIntInput(input)) {
                endPointIndex = input;
                buildResult();
                setFinished(true);
            }
            break;
        default:
            prompt = MessagesPrompt.PROMPT_ERROR;
            break;
        }
        tryCancelConversation(input);
    }

    @Override
    protected void buildResult() {
        if (constraint != null && startPointIndex != null && endPointIndex != null) {
            result = command + " " + constraint + " " + startPointIndex + " " + endPointIndex;
        } else {
            attempts++;
        }
    }
}
