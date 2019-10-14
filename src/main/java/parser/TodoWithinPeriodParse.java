package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

import java.text.ParseException;
import java.time.LocalDateTime;

public class TodoWithinPeriodParse extends TodoParse {

    public TodoWithinPeriodParse(String userInput) {
        super(userInput);
        this.checkType = "/between";
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();

        String dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
        String from;
        String to;
        try {
            from = dateTimeFromUser.split("-", 2)[0].trim();
            to = dateTimeFromUser.split("-", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        }
        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = DateTimeExtractor.extractDateTime(to, command);
            endDate = DateTimeExtractor.extractDateTime(from, command);
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
        return new AddCommand(command, taskDescription, startDate, endDate);
    }


}
