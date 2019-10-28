package parser;

import command.Command;
import command.TaskScheduleCommand;
import exception.DukeException;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * Extract the components required for the schedule command from the user input.
 *
 * @author Fauzan Adipratama
 * @version v1.3
 */
public class ScheduleParser extends IndexParser {

    private final int INDEX_INPUT = 0;
    private final int DATE_INPUT = 1;

    public ScheduleParser(String userInput, String command) {
        super(userInput, command);
    }


    /**
     * Parses the inputted text into its components and check whether the last inputted component is an index number
     * or a date.
     * @return the command to execute to schedule a task's duration by the selected Deadline or a custom deadline date
     * @throws DukeException if the inputted text does not match the expected format
     */
    @Override
    public Command parse() throws DukeException {
        super.extract();
        int type = checkInputType(taskFeatures);
        switch (type) {
        case INDEX_INPUT:
            int indexOfDeadline = extractDeadlineIndex(taskFeatures);
            return new TaskScheduleCommand(indexOfTask, indexOfDeadline);
        case DATE_INPUT:
            LocalDateTime dateOfDeadline = extractDeadlineDate(taskFeatures);
            return new TaskScheduleCommand(indexOfTask, dateOfDeadline);
        }

        return null;
    }

    private int extractDeadlineIndex(String taskFeatures) throws DukeException {
        String extractedIndex = taskFeatures.split(Flag.BY.getFlag(), 2)[1].trim();
        int convertedIndex;
        try {
            convertedIndex = Integer.parseInt(extractedIndex) - 1;
        } catch (NumberFormatException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new DukeException(DukeException.unknownUserCommand());
        }

        return convertedIndex;
    }

    private LocalDateTime extractDeadlineDate(String taskFeatures) throws DukeException {
        String extractedDate = taskFeatures.split(Flag.BY.getFlag(), 2)[1].trim();
        LocalDateTime convertedDate;
        try {
            convertedDate = DateTimeExtractor.extractDateTime(extractedDate, command);
        } catch (ParseException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new DukeException(DukeException.unknownUserCommand());
        }

        return convertedDate;
    }

    private int checkInputType(String taskFeatures) throws DukeException {
        String stringToCheck = taskFeatures.split(Flag.BY.getFlag(), 2)[1].trim();
        if (stringToCheck.isEmpty()) {
            throw new DukeException(DukeException.emptyDateOrTime());
        }
        if (stringToCheck.contains("/")) {
            return DATE_INPUT;
        }
        return INDEX_INPUT;
    }
}
