package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

import java.text.ParseException;
import java.time.LocalDateTime;

public class TodoParse {

    String userInput;
    String command;
    String taskFeatures;
    String checkType;
    String taskDescription;

    public TodoParse (String userInput) {
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

    private String removeCommandInput(String userInput)  throws DukeException {
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

    private static Command parseDuration(String userInput, String taskDescription, String checkType, String command)
            throws DukeException {
        int duration;

        String substring = userInput.split(checkType, 2)[1].trim();
        try {
            duration = Integer.parseInt(substring.split("\\s+", 2)[0].trim());
        } catch (NumberFormatException e) {
            throw new DukeException("Invalid duration format. Duration must be a number");
        }
        return new AddCommand(command, taskDescription, duration);
    }
}
