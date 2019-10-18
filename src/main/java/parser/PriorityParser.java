package parser;

import command.Command;
import command.PriorityCommand;
import exception.DukeException;

public class PriorityParser extends IndexParser {

    public PriorityParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        String priorityString = extractPriority(taskFeatures);
        return new PriorityCommand(indexOfTask, priorityString);
    }

    private String extractPriority(String taskFeatures) throws DukeException {
        String priorityString;
        try {
            String[] priorityCommandParts = taskFeatures.split(" ", 2);
            priorityString = priorityCommandParts[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyPriorityLevel());
        }
        return priorityString;
    }


}
