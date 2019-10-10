package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class HolidayConversation extends Conversation {
    private String command = "add";
    private String location;
    private String startDate;
    private String endDate;

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            prompt = MessagesPrompt.HOLIDAY_PROMPT_STARTER;
            state++;
            break;
        case 1:
            prompt = MessagesPrompt.HOLIDAY_PROMPT_STARTDATE;
            location = input;
            state++;
            break;
        case 2:
            prompt = MessagesPrompt.HOLIDAY_PROMPT_ENDDATE;
            startDate = input;
            state++;
            break;
        case 3:
            prompt = MessagesPrompt.HOLIDAY_PROMPT_SUCCESS;
            endDate = input;

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
    void buildResult() {
        result = command + " & " + location + " & " + startDate + " & " + endDate;
    }
}
