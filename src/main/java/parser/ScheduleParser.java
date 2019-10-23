package parser;

import command.Command;
import command.TaskScheduleCommand;
import exception.DukeException;

public class ScheduleParser extends IndexParser {

    int indexOfDeadline;

    public ScheduleParser(String userInput, String command) {
        super(userInput, command);
    }


    @Override
    public Command parse() throws DukeException {
        super.extract();
        indexOfDeadline = extractDeadline(taskFeatures);

        return new TaskScheduleCommand(indexOfTask, indexOfDeadline);
    }

    private int extractDeadline(String taskFeatures) throws DukeException {
        String extractedIndex = taskFeatures.split(Flag.BY.getFlag(), 2)[1].trim();
        int convertedIndex;
        try {
            convertedIndex = Integer.parseInt(extractedIndex) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException(DukeException.unknownUserCommand());
        }

        return convertedIndex;
    }
}
