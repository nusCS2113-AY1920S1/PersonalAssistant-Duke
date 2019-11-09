package chronologer.parser;

import chronologer.command.Command;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiMessageHandler;

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

    public abstract Command parse() throws ChronologerException;

    void extract() throws ChronologerException {
        this.taskFeatures = removeCommandInput(userInput);
        this.indexOfTask = parseIndex(taskFeatures);
    }

    private int parseIndex(String taskFeatures) throws ChronologerException {
        Integer index;
        try {
            index = Integer.parseInt(taskFeatures.split("\\s+", 2)[0].trim()) - 1;
        } catch (NumberFormatException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            UiMessageHandler.outputMessage(ChronologerException.unknownUserCommand());
            throw new ChronologerException(ChronologerException.unknownUserCommand());
        }
        if (index < 0) {
            UiMessageHandler.outputMessage(NEGATIVE_NUM_DETECTED);
            throw new ChronologerException(NEGATIVE_NUM_DETECTED);
        }
        return index;
    }
}
