package duke.logic.parser.shortcut;

import duke.logic.command.shortcut.SetShortcutCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.shortcut.Shortcut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static duke.logic.parser.commons.CliSyntax.KEYWORDS;
import static java.util.Objects.requireNonNull;


public class SetShortcutCommandParser implements SubCommandParser<SetShortcutCommand> {
    private static final String MESSAGE_CONTAIN_KEYWORD = "[%s] is a keyword and cannot be set as shortcut name.";

    @Override
    public SetShortcutCommand parse(String subCommandAndArgs) throws ParseException {
        String shortcutName = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        if (containsKeywords(shortcutName)) {
            throw new ParseException(String.format(MESSAGE_CONTAIN_KEYWORD, shortcutName));
        }
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        Shortcut shortcut = new Shortcut(shortcutName, getUserInputs(args));
        return new SetShortcutCommand(shortcut);
    }

    private List<String> getUserInputs(String userInputsString) {
        return new ArrayList<String>(Arrays.asList(userInputsString.split(";")));
    }

    private boolean containsKeywords(String word) {
        requireNonNull(word);

        return KEYWORDS.contains(word.strip());
    }
}
