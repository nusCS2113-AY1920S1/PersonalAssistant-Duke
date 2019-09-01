import java.util.Arrays;
import java.util.List;

public class Parser {
    public static Command parse(String input) throws DukeException {
        List<String> words = Arrays.asList(input.split(" "));
        String keyword = words.get(0);
        List<String> arguments = words.subList(1, words.size());

        switch (keyword) {
            case "list":
                return new ListCommand();
            case "find":
                return new FindCommand(arguments);
            case "delete":
                return new DeleteCommand(arguments);
            case "done":
                return new DoneCommand(arguments);
            case "todo":
                return new AddTodoCommand(arguments);
            case "deadline":
                return new AddDeadlineCommand(arguments);
            case "event":
                return new AddEventCommand(arguments);
            case "bye":
                return new ByeCommand();
            default:
                throw new DukeException("Please enter a valid command.");
        }
    }
}
