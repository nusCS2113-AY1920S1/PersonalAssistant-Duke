package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * Extract the components required to add a TodoWithinPeriod task.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class TodoWithinPeriodParser extends TodoParser {

    public TodoWithinPeriodParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.BETWEEN.getFlag();
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        LocalDateTime startDate = extractStartDate(taskFeatures);
        LocalDateTime endDate = extractEndDate(taskFeatures);

        return new AddCommand(command, taskDescription, startDate, endDate);
    }

    private LocalDateTime extractStartDate(String taskFeatures) throws DukeException {
        String dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
        String from;
        try {
            from = dateTimeFromUser.split("-", 2)[0].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        }
        LocalDateTime startDate;
        try {
            startDate = DateTimeExtractor.extractDateTime(from, command);
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
        return startDate;
    }

    private LocalDateTime extractEndDate(String taskFeatures) throws DukeException {
        String dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
        String to;
        try {
            to = dateTimeFromUser.split("-", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        }
        LocalDateTime endDate;
        try {
            endDate = DateTimeExtractor.extractDateTime(to, command);
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
        return endDate;
    }
}
