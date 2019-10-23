package parser;

import command.Command;
import exception.DukeException;

public abstract class IndexParser extends Parser {

    Integer indexOfTask;

    public IndexParser(String userInput, String command) {
        super(userInput, command);
    }

    public abstract Command parse() throws DukeException;

    void extract() throws DukeException {
        this.taskFeatures = removeCommandInput(userInput);
        this.indexOfTask = parseIndex(taskFeatures);
    }

    private int parseIndex(String taskFeatures) throws DukeException {
        Integer index;
        try {
            index = Integer.parseInt(taskFeatures.split("\\s+", 2)[0].trim()) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException(DukeException.unknownUserCommand());
        }
        if (index < 0) {
            throw new DukeException("Non-positive number for index detected."
                + " Please input a positive number for task index.");
        }
        return index;
    }
}
