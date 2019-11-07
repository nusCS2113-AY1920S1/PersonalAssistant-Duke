package duke.logic.parser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import duke.logic.command.AddCommand;
import duke.exception.DukeException;

/**
 * Class that parsers the parameters of the add command and creates the corresponding command
 */
public class AddCommandParser implements Parser<AddCommand> {
    private static List<String> priorityAllowed = Arrays.asList("high", "medium", "low", "h", "m", "l");

    private String description;
    private String taskType;

    private int duration = 0;
    private String priority = "L";
    private Optional<LocalDateTime> dateTime = Optional.empty();
    private Optional<String> recurrence = Optional.empty();

    /**
     * Method that takes in the raw parameters and converts it to the respective keywords and fields
     * @param rawParameters of the command
     * @throws DukeException throws an exception of wrong format entered
     */
    private void getKeywordAndFields(String rawParameters) throws DukeException {
        String[] splitParameters = rawParameters.split(" -");

        for (int i = 1; i < splitParameters.length; i++) {
            String[] s = splitParameters[i].split(" ", 2);
            if (s.length == 1) {
                throw new DukeException("Please enter something for me to add!");
            }
            String keyword = s[0];
            String field = s[1];
            switch (keyword) {
            case "r":
                recurrence = Optional.of(field);
                break;
            case "d":
                try {
                    duration = Integer.parseInt(field);
                } catch (NumberFormatException e) {
                    throw new DukeException("Please enter a numerical field for the duration!");
                }
                break;
            case "t":
                this.dateTime = Optional.of(DateTimeParser.parseDateTime(field));
                break;
            case "p":
                String p = field.trim().toLowerCase();
                if (!priorityAllowed.contains(p)) {
                    throw new DukeException("Please enter a valid priority description!");
                }
                this.priority = p;
                break;
            default:
                throw new DukeException("I don't know which field you are trying to add!");

            }
        }
    }

    /**
     * Function to split the string into the respective task type and description
     * @param s command string
     */
    private void getTypeAndDescription(String s) {
        String[] typeArray = s.split(" ", 2);
        this.taskType = typeArray[0];
        String[] descriptionArray = typeArray[1].split(" -", 2);
        this.description = descriptionArray[0];
    }

    /**
     * Inherited method that takes in an optional string and a string for the command
     * @param filter an optional string of the filter of the task
     * @param args rest of the arguments to be parsed into the correct attributes
     * @return returns the correct addCommand
     * @throws DukeException throws an exception if submethods throws an exception
     */
    public AddCommand parse(Optional<String> filter, String args) throws DukeException {
        getTypeAndDescription(args);
        getKeywordAndFields(args);
        return new AddCommand(filter, dateTime, recurrence, description, taskType, duration, priority);
    }
}
