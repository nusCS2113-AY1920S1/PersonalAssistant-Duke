package duke.logic.conversations;

import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeDateTimeParseException;
import duke.logic.parsers.ParserTimeUtil;

public class DeadlineConversation extends Conversation {
    private static final String command = "deadline";
    private String description;
    private String date;

    public DeadlineConversation() {
        super();
        prompt = MessagesPrompt.DEADLINE_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        switch (state) {
        case 1:
            prompt = MessagesPrompt.DEADLINE_PROMPT_DATE;
            description = input;
            state++;
            break;
        case 2:
            try {
                ParserTimeUtil.parseStringToDate(input);
                date = input;
                buildResult();
                setFinished(true);
            } catch (DukeDateTimeParseException e) {
                attempts++;
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
        result = command + " " + description + " by " + date;
    }
}
