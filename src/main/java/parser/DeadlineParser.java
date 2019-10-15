package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

import java.text.ParseException;
import java.time.LocalDateTime;

public class DeadlineParser extends DescriptionParser {

    LocalDateTime startDate;

    public DeadlineParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.BY.getFlag();
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();

        String dateTimeFromUser;
        try {
            dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
            startDate = DateTimeExtractor.extractDateTime(dateTimeFromUser, command);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }

        return new AddCommand(command, taskDescription, startDate, null);
    }
}
