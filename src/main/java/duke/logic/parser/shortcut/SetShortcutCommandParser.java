package duke.logic.parser.shortcut;

import duke.logic.command.shortcut.SetShortcutCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.shortcut.Shortcut;
import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser that parses {@code SetShortcutCommand}.
 */
public class SetShortcutCommandParser implements SubCommandParser<SetShortcutCommand> {
    private static final Pattern NAME_AND_COMMANDS_FORMAT = Pattern.compile("^\\[(\\w+)\\]\\s*\\[([^\\[\\]]*)\\]");

    private static final String MESSAGE_EMPTY_NAME = "Shortcut name cannot be blank";
    private static final String MESSAGE_EMPTY_COMMAND = "Command string format is incorrect.";
    private static final String MESSAGE_INCORRECT_FORMAT = "Incorrect command format for shortcut."
        + " Please refer to User Guide for correct usage";
    private static final String COMMAND_SPLITTER = ";";

    @Override
    public SetShortcutCommand parse(String subCommandAndArgs) throws ParseException {
        String shortcutName = getShortcutName(subCommandAndArgs);
        String commandString = getCommandString(subCommandAndArgs);

        Shortcut shortcut = new Shortcut(shortcutName, getUserInputs(commandString));
        return new SetShortcutCommand(shortcut);
    }

    private List<String> getUserInputs(String userInputsString) throws ParseException {
        assert (userInputsString != null);

        if (userInputsString.isBlank()) {
            return new ArrayList<>();
        }

        List<String> commandStrings = new ArrayList<String>(
            Arrays.asList(userInputsString.split(COMMAND_SPLITTER, -1)));

        if (commandStrings.isEmpty()) {
            throw new ParseException(MESSAGE_INCORRECT_FORMAT);
        }

        for (String command : commandStrings) {
            if (command.isBlank()) {
                throw new ParseException(MESSAGE_EMPTY_COMMAND);
            }
        }

        return commandStrings;
    }

    private String getShortcutName(String subCommandAndArgs) throws ParseException {
        final Matcher matcher = NAME_AND_COMMANDS_FORMAT.matcher(subCommandAndArgs);

        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_INCORRECT_FORMAT);
        }

        if (matcher.group(1).isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_NAME);
        }

        return matcher.group(1);
    }

    private String getCommandString(String subCommandAndArgs) throws ParseException {
        final Matcher matcher = NAME_AND_COMMANDS_FORMAT.matcher(subCommandAndArgs);

        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_INCORRECT_FORMAT);
        }

        return matcher.group(2);
    }
}
