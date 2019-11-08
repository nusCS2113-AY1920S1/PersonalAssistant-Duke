package duke.logic.parsers.commandparsers;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.ProfileSetPreferenceCommand;
import duke.logic.parsers.ParserUtil;

public class ProfileSetParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    /**
     * Constructs the ProfileSetParser.
     */
    public ProfileSetParser(String input) {
        this.input = input;
    }
    @Override
    public Command parse() throws ParseException {
        String category = ParserUtil.getFieldInList(ZERO, TWO, input);
        String setting = ParserUtil.getFieldInList(ONE, TWO, input);
        return new ProfileSetPreferenceCommand(category, setting);
    }
}
