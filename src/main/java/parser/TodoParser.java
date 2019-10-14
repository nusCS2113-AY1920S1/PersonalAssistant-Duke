package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

public class TodoParser {

    String userInput;
    String command;
    String taskFeatures;
    String checkType;
    String taskDescription;

    public TodoParser(String userInput) {
        this.userInput = userInput;
        this.command = "todo";
    }

    public Command parse() throws DukeException {
        extract();

        return new AddCommand(command, taskDescription, null, null);
    }

    void extract() throws DukeException {
        this.taskFeatures = removeCommandInput(userInput);
        this.taskDescription = parseDetails(taskFeatures, checkType);
    }

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
