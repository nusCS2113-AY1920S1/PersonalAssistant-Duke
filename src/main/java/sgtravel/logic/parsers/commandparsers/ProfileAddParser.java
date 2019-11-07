package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ChronologyAfterPresentException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.ProfileAddCommand;
import sgtravel.logic.commands.Command;
import sgtravel.logic.parsers.ParserTimeUtil;

import java.time.LocalDateTime;

/**
 * Parses the user inputs into suitable format for AddProfileCommand.
 */
public class ProfileAddParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    /**
     * Constructs the AddProfileParser.
     *
     * @param input The user input.
     */
    public ProfileAddParser(String input) {
        this.input = input;
    }

    /**
     * Parses user input and constructs an AddProfileCommand object.
     *
     * @return AddProfileCommand object.
     * @throws ParseException If AddProfileCommand object cannot be created from user input.
     */
    @Override
    public Command parse() throws ParseException {
        String[] token = input.split(" ");
        String name = "";
        for (int i = ZERO; i < token.length - ONE; i++) {
            name += token[i] + " ";
        }
        LocalDateTime birthday = ParserTimeUtil.parseStringToDate(token[token.length - ONE]);
        if (birthday.isAfter(LocalDateTime.now())) {
            throw new ChronologyAfterPresentException();
        }
        return new ProfileAddCommand(name, birthday);
    }
}
