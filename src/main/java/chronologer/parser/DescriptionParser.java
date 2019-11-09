package chronologer.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chronologer.command.Command;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiTemporary;

/**
 * Backbone of all parsers that process input based on strings.
 *
 * @author Fauzan
 * @version v1.0
 */
public abstract class DescriptionParser extends Parser {

    String checkType;
    String taskDescription;

    public DescriptionParser(String userInput, String command) {
        super(userInput, command);
    }

    public abstract Command parse() throws ChronologerException;

    void extract() throws ChronologerException {
        this.taskFeatures = removeCommandInput(userInput);
        this.taskDescription = parseDetails(taskFeatures, checkType);
    }

    private String parseDetails(String taskFeatures, String checkType) throws ChronologerException {
        if (checkType == null) {
            return taskFeatures;
        }
        if (hasModCode) {
            return this.command;
        }
        String description = taskFeatures.split(checkType, 2)[0].trim();
        if (description.isEmpty()) {
            UiTemporary.printOutput(ChronologerException.emptyUserDescription());
            throw new ChronologerException(ChronologerException.emptyUserDescription());
        }
        return description;
    }

    // @@author hanskw4267
    protected String extractModCode(String taskFeatures) throws ChronologerException {
        Pattern patt = Pattern.compile("/m\\s*[A-Z,a-z]{2,3}[1-9]{1}\\d{3}[A-Z,a-z]?");
        Matcher matcher = patt.matcher(taskFeatures);
        if (matcher.find()) {
            return matcher.group().split("\\s")[1].toUpperCase(); // you can get it from desired index as well
        } else {
            logger.writeLog("Missing module code", this.getClass().getName(), userInput);
            return null;
        }
    }
    // @@author
}
