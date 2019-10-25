package duke.logic.parsers;

import duke.logic.commands.Command;
import duke.logic.commands.PromptCommand;

public class PromptParser {
    public static Command parseCommand(String prompt) {
        return new PromptCommand(prompt);
    }
}
