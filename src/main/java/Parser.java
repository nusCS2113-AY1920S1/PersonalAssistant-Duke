import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public static Command parse(String fullCommand)throws DukeException {
        List<String> splitInput = new ArrayList<String>(
                Arrays.asList(fullCommand.split(" ")));
        if (fullCommand.equals("list")) {
            return new ListCommand();
        } else if (fullCommand.equals("bye")) {
            return new ByeCommand();
        } else if (splitInput.get(0).equals("done")) {
            return new DoneCommand(splitInput);
        } else if (splitInput.get(0).equals("todo")) {
            return new AddToDoCommand(fullCommand);
        } else if (splitInput.get(0).equals("deadline")) {
            return new AddDeadlineCommand(splitInput);
        } else if (splitInput.get(0).equals("event")) {
            return new AddEventCommand(splitInput);
        } else if (splitInput.get(0).equals("delete")) {
            return new DeleteCommand(splitInput);
        } else if (splitInput.get(0).equals("find")) {
            return new FindCommand(splitInput);
        } else {
            throw new DukeException(" Please enter a valid command");
        }
    }
}