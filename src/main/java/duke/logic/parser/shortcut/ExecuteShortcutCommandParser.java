package duke.logic.parser.shortcut;

import duke.logic.command.shortcut.ExecuteShortcutCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;

public class ExecuteShortcutCommandParser implements SubCommandParser<ExecuteShortcutCommand> {
    private static final String MESSAGE_NAME_NOT_FOUND = "Please specify the shortcut name";

    @Override
    public ExecuteShortcutCommand parse(String subCommandAndArgs) throws ParseException {
        String shortcutName = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        if (shortcutName.isBlank()) {
            throw new ParseException(MESSAGE_NAME_NOT_FOUND);
        }
        return new ExecuteShortcutCommand(shortcutName);
    }

}
