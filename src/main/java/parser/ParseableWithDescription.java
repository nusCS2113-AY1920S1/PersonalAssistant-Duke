package parser;

import command.Command;
import exception.DukeException;

public interface ParseableWithDescription {

    Command parse();

    private String removeCommandInput(String userInput) throws DukeException {
        String taskFeatures;
        try {
            taskFeatures = userInput.split("\\s+", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyUserDescription());
        }
        return taskFeatures;
    }

    private String parseDetails(String taskFeatures, String checkType) {
        if (checkType == null) {
            return taskFeatures;
        }
        return taskFeatures.split(checkType,2)[0].trim();
    }
}
