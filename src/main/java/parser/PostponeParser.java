package parser;

import command.Command;
import command.PostponeCommand;
import exception.DukeException;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * Extract the components required for the postpone command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class PostponeParser extends IndexParser {

    private LocalDateTime newFromDate = null;
    private LocalDateTime newToDate = null;

    public PostponeParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        String[] postponeCommandParts = taskFeatures.split("\\s+", 2);
        String dateString;
        try {
            dateString = postponeCommandParts[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        }
        if (dateString.contains("-")) {
            extractEventDates(dateString);
            return new PostponeCommand(indexOfTask, newFromDate, newToDate);
        } else {
            extractDeadlineDates(dateString);
            return new PostponeCommand(indexOfTask, newFromDate);
        }
    }


    private void extractEventDates(String dateString) throws DukeException {
        try {
            String obtainStartDate = dateString.split("-", 2)[0].trim();
            newFromDate = DateTimeExtractor.extractDateTime(obtainStartDate, command);
            String obtainEndDate = dateString.split("-", 2)[1].trim();
            newToDate = DateTimeExtractor.extractDateTime(obtainEndDate, command);
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
    }

    private void extractDeadlineDates(String dateString) throws DukeException {
        try {
            newFromDate = DateTimeExtractor.extractDateTime(dateString, command);
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
    }
}
