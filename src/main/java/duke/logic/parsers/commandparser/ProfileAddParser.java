package duke.logic.parsers.commandparser;

import duke.commons.Messages;
import duke.commons.exceptions.BirthdayError;
import duke.commons.exceptions.ParseException;
import duke.logic.commands.ProfileAddCommand;
import duke.logic.commands.Command;
import duke.logic.parsers.ParserTimeUtil;

import java.time.LocalDateTime;
import java.time.Period;

/**
 * Parses the user inputs into suitable format for AddProfileCommand.
 */
public class ProfileAddParser extends CommandParser {

    private String name;
    private LocalDateTime birthday;

    /**
     * Parses user input into name and birthday.
     * @param input The User input
     */
    public ProfileAddParser(String input) throws ParseException, BirthdayError {
        String[] token = input.split(" ");
        this.name = "";
        for (int i = 0; i < token.length - 1; i++) {
            this.name += token[i] + " ";
        }
        this.birthday = ParserTimeUtil.parseStringToDate(token[token.length - 1]);
        if (Period.between(this.birthday.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears() < 0) {
            throw new BirthdayError();
        }
    }

    /**
     * Constructs AddProfile object.
     * @return AddProfile object
     */
    @Override
    public Command parse() {
        return new ProfileAddCommand(name, birthday);
    }
}
