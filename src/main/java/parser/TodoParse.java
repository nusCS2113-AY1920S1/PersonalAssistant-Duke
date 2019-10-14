package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

public class TodoParse {

    String userInput;
    String command;

    public TodoParse (String userInput) {
        this.userInput = userInput;
        this.command = "todo";
    }

    private Command parse() throws DukeException {
        String taskFeatures;

        taskFeatures = removeCommandInput(userInput);
        String checkType = checkFlags(taskFeatures);
        String taskDescription = parseDetails(taskFeatures, checkType);

        switch(checkType) {
            case "/between": {
                return parseToDoPeriod(taskFeatures, taskDescription, checkType, command);
            }
            case "/for": {
                return parseDuration(taskFeatures, taskDescription, checkType, command);
            }
            default:
                return new AddCommand(command, taskDescription, null, null);
        }
    }

    private String removeCommandInput(String userInput)  throws DukeException {
        String taskFeatures;
        try {
            taskFeatures = userInput.split("\\s+", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyUserDescription());
        }
        return taskFeatures;
    }

    private String checkFlags(String taskFeatures) {
        String checkType;

        checkType = "/between";
        String[] taskDetails = taskFeatures.split(checkType, 2);
        if (taskDetails.length != 1) {
            return "between";
        }
        checkType = "/for";
        taskDetails = taskFeatures.split(checkType, 2);
        if (taskDetails.length != 1) {
            return "for";
        }
        return "";
    }

    private String parseDetails(String taskFeatures, String checkType) {
        if (checkType.equals("")) {
            return taskFeatures;
        }
        return taskFeatures.split(checkType,2)[0].trim();
    }

}
