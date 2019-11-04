package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.AddProfileCommand;
import duke.logic.commands.Command;
import duke.logic.parsers.ParserTimeUtil;

import java.time.LocalDateTime;

/**
 * Parses the user inputs into suitable format for AddProfileCommand.
 */
public class AddProfileParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    /**
     * Constructs the AddProfileParser.
     */
    public AddProfileParser(String input) {
        this.input = input;
    }

    /**
     * Parses user input and constructs an AddProfileCommand object.
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
        return new AddProfileCommand(name, birthday);
    }
}
