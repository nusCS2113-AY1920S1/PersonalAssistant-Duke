package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.ProfileSetPreferenceCommand;
import sgtravel.logic.parsers.ParserUtil;

/**
 * Parses the input in to suitable parts for ProfileSetPreferenceCommand.
 */
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

    /**
     * Parses the input in to suitable parts for ProfileSetPreferenceCommand.
     *
     * @return The constructed ProfileSetPreferenceCommand.
     * @throws ParseException If there is a error in input.
     */
    @Override
    public Command parse() throws ParseException {
        String category = ParserUtil.getFieldInList(ZERO, TWO, input);
        String setting = ParserUtil.getFieldInList(ONE, TWO, input);
        return new ProfileSetPreferenceCommand(category, setting);
    }
}
