package duke.logic.parser.shortcut;

import duke.logic.command.shortcut.ExecuteShortCutCommand;
import duke.logic.command.shortcut.SetShortcutCommand;
import duke.logic.parser.commons.BakingHomeParser;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.BakingHome;
import duke.model.shortcut.Shortcut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static duke.logic.parser.commons.CliSyntax.KEYWORDS;
import static java.util.Objects.requireNonNull;

public class ExecuteShortcutCommandParser implements SubCommandParser<ExecuteShortCutCommand> {
    private static final String MESSAGE_NAME_NOT_FOUND = "Please specify the shortcut name";

    @Override
    public ExecuteShortCutCommand parse(String subCommandAndArgs) throws ParseException {
        String shortcutName = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        if (shortcutName.equals("")) {
            throw new ParseException(MESSAGE_NAME_NOT_FOUND);
        }
        return new ExecuteShortCutCommand(shortcutName);
    }

}
