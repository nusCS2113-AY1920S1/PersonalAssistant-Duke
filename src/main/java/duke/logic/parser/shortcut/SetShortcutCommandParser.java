package duke.logic.parser.shortcut;

import duke.logic.command.shortcut.SetShortcutCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.shortcut.Shortcut;
import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser that parses {@code SetShortcutCommand}.
 */
public class SetShortcutCommandParser implements SubCommandParser<SetShortcutCommand> {
    private static String MESSAGE_EMPTY_NAME = "Shortcut name cannot be blank.";
    private static String MESSAGE_EMPTY_COMMAND = "The commands in a shortcut cannot be blank.";
    private static String COMMAND_SPLITTER = ";";

    @Override
    public SetShortcutCommand parse(String subCommandAndArgs) throws ParseException {
        String shortcutName = SubCommandParser.getSubCommandWord(subCommandAndArgs);

        if (shortcutName.isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_NAME);
        }

        String args = SubCommandParser.getArgs(subCommandAndArgs);

        Shortcut shortcut = new Shortcut(shortcutName, getUserInputs(args));
        return new SetShortcutCommand(shortcut);
    }

    private List<String> getUserInputs(String userInputsString) throws ParseException {
        assert (userInputsString != null);

        if (userInputsString.isBlank()) {
            return new ArrayList<>();
        }

        List<String> commandStrings = new ArrayList<String>(Arrays.asList(userInputsString.split(COMMAND_SPLITTER, -1)));

        if (commandStrings.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_COMMAND);
        }

        for (String command : commandStrings) {
            if (command.isBlank()) {
                throw new ParseException(MESSAGE_EMPTY_COMMAND);
            }
        }

        return commandStrings;
    }

}
