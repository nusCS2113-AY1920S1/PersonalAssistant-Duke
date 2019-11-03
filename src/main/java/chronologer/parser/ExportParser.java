package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.ExportCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the export command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.2
 */
public class ExportParser extends DescriptionParser {

    private boolean hasDeadlineFlag = false;
    private boolean hasEventFlag = false;
    private boolean hasTodoFlag = false;

    ExportParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        extractFlags(taskFeatures);
        String fileName = removeFlags(taskFeatures);
        return new ExportCommand(fileName, hasDeadlineFlag, hasEventFlag, hasTodoFlag);
    }

    private void extractFlags(String taskFeatures) {
        if (taskFeatures.contains(Flag.DEADLINE.getFlag())) {
            hasDeadlineFlag = true;
        }
        if (taskFeatures.contains(Flag.EVENT.getFlag())) {
            hasEventFlag = true;
        }
        if (taskFeatures.contains(Flag.TODO.getFlag())) {
            hasTodoFlag = true;
        }
    }

    private String removeFlags(String taskFeatures) {
        return taskFeatures.replaceAll(Flag.DEADLINE.getFlag(), "")
            .replaceAll(Flag.EVENT.getFlag(), ""
            ).replaceAll(Flag.TODO.getFlag(), "");
    }

}
