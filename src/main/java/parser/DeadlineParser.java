package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * Extract the components to add a deadline .
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class DeadlineParser extends DescriptionParser {

    public DeadlineParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.BY.getFlag();
    }


    @Override
    public Command parse() throws DukeException {
        super.extract();
        LocalDateTime startDate = extractStartDate(taskFeatures);

        return new AddCommand(command, taskDescription, startDate, null);
    }

    private LocalDateTime extractStartDate(String taskFeatures) throws DukeException {
        String dateTimeFromUser;
        LocalDateTime startDate;
        try {
            dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
            startDate = DateTimeExtractor.extractDateTime(dateTimeFromUser, command);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyDateOrTime());
        } catch (ParseException e) {
            throw new DukeException(DukeException.wrongDateOrTime());
        }
        return startDate;
    }
}
