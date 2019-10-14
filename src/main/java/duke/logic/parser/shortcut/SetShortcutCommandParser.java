package duke.logic.parser.shortcut;

import duke.logic.command.shortcut.SetShortcutCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.shortcut.Shortcut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SetShortcutCommandParser implements SubCommandParser<SetShortcutCommand> {

    @Override
    public SetShortcutCommand parse(String subCommandAndArgs) throws ParseException {
        String shortcutName = SubCommandParser.getSubCommandWord(subCommandAndArgs);

        String args = SubCommandParser.getArgs(subCommandAndArgs);

        Shortcut shortcut = new Shortcut(shortcutName, getUserInputs(args));
        return new SetShortcutCommand(shortcut);
    }

    private List<String> getUserInputs(String userInputsString) {
        return new ArrayList<String>(Arrays.asList(userInputsString.split(";")));
    }

}
