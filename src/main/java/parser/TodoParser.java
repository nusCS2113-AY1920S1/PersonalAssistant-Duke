package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

public class TodoParser extends Parse {

    String checkType;
    String taskDescription;

    public TodoParser(String userInput, String command) {
        super(userInput, command);
    }

    public Command parse() throws DukeException {
        extract();

        return new AddCommand(command, taskDescription, null, null);
    }

    void extract() throws DukeException {
        this.taskFeatures = removeCommandInput(userInput);
        this.taskDescription = parseDetails(taskFeatures, checkType);
    }

    private String parseDetails(String taskFeatures, String checkType) {
        if (checkType == null) {
            return taskFeatures;
        }
        return taskFeatures.split(checkType,2)[0].trim();
    }
}
