package duke.logic.conversations;

import duke.commons.Messages;

public class FindPathConversation extends Conversation {
    private static final String command = "findPath";
    private String constraint;
    private String startPointIndex;
    private String endPointIndex;

    public FindPathConversation() {
        super();
        prompt = Messages.FINDPATH_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        switch (state) {
        case 1:
            prompt = Messages.PROMPT_ROUTE_STARTING_POINT;
            constraint = input;
            state++;
            break;
        case 2:
            if (isIntInput(input)) {
                startPointIndex = input;
                prompt = Messages.PROMPT_ROUTE_ENDING_POINT;
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
            prompt = Messages.PROMPT_ERROR;
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
