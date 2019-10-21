package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

import java.text.ParseException;
import java.time.LocalDateTime;

public class EventParser extends DescriptionParser {

    public EventParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.AT.getFlag();
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();

        LocalDateTime fromDate;
        LocalDateTime toDate;
        try {
            String dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
            fromDate = extractFromDate(dateTimeFromUser);
            toDate = extractToDate(dateTimeFromUser);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        }
        assert toDate != null;
        assert fromDate != null;
        return new AddCommand(command, taskDescription, fromDate, toDate);
    }

    private LocalDateTime extractFromDate(String dateTimeFromUser) throws DukeException {
        try {
            String fromDateString = dateTimeFromUser.split("-", 2)[0].trim();
            return DateTimeExtractor.extractDateTime(fromDateString, command);
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
    }

    private LocalDateTime extractToDate(String dateTimeFromUser) throws DukeException {
        try {
            String toDateString = dateTimeFromUser.split("-", 2)[1].trim();
            return DateTimeExtractor.extractDateTime(toDateString, command);
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
    }


}
