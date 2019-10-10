package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class GetBusRouteConversation extends Conversation {
    private String command = "busRoute";
    private String bus;

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            prompt = MessagesPrompt.GETBUSROUTE_PROMPT_STARTER;
            state++;
            break;
        case 1:
            if (input.contains(" ")) {
                prompt = MessagesPrompt.PROMPT_SPACES;
                badAttempt();
            } else if (input.matches("-?\\d+")) {
                prompt = MessagesPrompt.PROMPT_NOT_STRING;
                badAttempt();
            } else {
                prompt = MessagesPrompt.GETBUSROUTE_PROMPT_SUCCESS;
                bus = input;

                buildResult();
                isDone = true;
            }
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
    void buildResult() {
        result = command + " & " + bus;
    }
}
