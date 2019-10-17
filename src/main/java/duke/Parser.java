package duke;

import duke.exceptions.DukeException;
import duke.logic.commands.AddBatchCommand;
import duke.logic.commands.AddLockerCommand;
import duke.logic.commands.ByeCommand;
import duke.logic.commands.Command;
import duke.logic.commands.DeleteLockerCommand;
import duke.logic.commands.EditLockerCommand;
import duke.logic.commands.ListCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    /**
     * this function is used to parse the command entered by the user.
     * @param fullCommand stores the command entered by the user
     * @return objects of type Command depending on the command given by the user
     * @throws DukeException when the user inputs invalid command
     */

    public static Command parse(String fullCommand) throws DukeException {
        List<String> splitInput = new ArrayList<String>(
                Arrays.asList(fullCommand.split(" ")));
        String inputTask = splitInput.get(0);
        if (inputTask.equalsIgnoreCase("addLocker")) {
            return new AddLockerCommand(splitInput);
        } else if (fullCommand.equalsIgnoreCase("bye")) {
            return new ByeCommand();
        } else if (fullCommand.equalsIgnoreCase("list")) {
            return new ListCommand();
        } else if (inputTask.equalsIgnoreCase("addBatch")) {
            return new AddBatchCommand(splitInput);
        } else if (inputTask.equalsIgnoreCase("delete")) {
            return new DeleteLockerCommand(splitInput);
        } else if (inputTask.equalsIgnoreCase("edit")) {
            return new EditLockerCommand(splitInput);
        } else  {
            throw new DukeException(" Invalid command");
        }
    }
}
