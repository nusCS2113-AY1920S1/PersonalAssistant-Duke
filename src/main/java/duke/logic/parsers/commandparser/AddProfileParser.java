package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddProfileCommand;
import duke.logic.commands.Command;
import duke.logic.parsers.ParserTimeUtil;

import java.time.LocalDateTime;

/**
 * Parses the user inputs into suitable format for AddProfileCommand.
 */
public class AddProfileParser extends CommandParser {

    private String name;
    private LocalDateTime birthday;

    /**
     * Parses user input into name and birthday.
     * @param input The User input
     */
    public AddProfileParser(String input) throws DukeDateTimeParseException {
        String[] token = input.split(" ");
        this.name = "";
        for (int i = 0; i < token.length - 1; i++) {
            this.name += token[i] + " ";
        }
        this.birthday = ParserTimeUtil.parseStringToDate(token[token.length - 1]);
    }

    /**
     * Constructs AddProfile object.
     * @return AddProfile object
     */
    @Override
    public Command parse() throws DukeException {
        return new AddProfileCommand(name, birthday);
    }
}
