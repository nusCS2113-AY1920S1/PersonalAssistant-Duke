package duke.parsers.prompts;

import duke.commons.PromptMessages;
import duke.ui.Ui;

public class HolidayPrompt extends Prompt {
    private String command = "add";
    private String location;
    private String startDate;
    private String endDate;

    public HolidayPrompt(Ui ui) {
        super();
    }

    @Override
    public void execute(String input, Ui ui) {
        switch(state) {
            case 0:
                ui.show(PromptMessages.HOLIDAY_PROMPT_STARTER);
                state++;
                break;
            case 1:
                ui.show(PromptMessages.HOLIDAY_PROMPT_STARTDATE);
                location = input;
                state++;
                break;
            case 2:
                ui.show(PromptMessages.HOLIDAY_PROMPT_ENDDATE);
                startDate = input;
                state++;
                break;
            case 3:
                ui.show(PromptMessages.HOLIDAY_PROMPT_SUCCESS   );
                endDate = input;

                buildResult();
                isDone = true;
                break;
        }
        if (attempts > 4) {
            ui.show(PromptMessages.PROMPT_TOO_MANY_ATTEMPTS);
            isDone = true;
            isCancelled = true;
        }
    }

    @Override
    void buildResult() {
        result = command + " & " + location + " & " + startDate + " & " + endDate;
    }
}
