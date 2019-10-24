package parser;

import command.Command;
import exception.DukeException;

/**
 * Backbone of all parsers that process input based on list index.
 *
 * @author Fauzan
 * @version v1.0
 */
public abstract class IndexParser extends Parser {

    Integer indexOfTask;
    private static final String NEGATIVE_NUM_DETECTED = "Please input a positive number for task index.";

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
            throw new DukeException(NEGATIVE_NUM_DETECTED);
        }
        return index;
    }
}
