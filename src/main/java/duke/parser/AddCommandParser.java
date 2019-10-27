package duke.parser;

import duke.command.AddCommand;
import duke.exception.DukeException;
import duke.extensions.Recurrence;

import java.time.LocalDateTime;
import java.util.Optional;

public class AddCommandParser implements Parser<AddCommand> {
    String description;
    String taskType;

    int duration = 0;
    Optional<LocalDateTime> dateTime = Optional.empty();
    Optional<String> recurrence = Optional.empty();

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
            }
        }
    }

    private void getTypeAndDescription(String s) {
        String[] typeArray = s.split(" ", 2);
        this.taskType = typeArray[0];
        String[] descriptionArray = typeArray[1].split(" -", 2);
        this.description = descriptionArray[0];
    }

    @Override
    public AddCommand parse(Optional<String> filter, String args) throws DukeException {
        getTypeAndDescription(args);
        getKeywordAndFields(args);
        return new AddCommand(filter, dateTime, recurrence, description, taskType, duration);
    }
}
