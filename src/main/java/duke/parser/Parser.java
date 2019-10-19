package duke.parser;

import duke.exceptions.DukeException;

import duke.logic.commands.Command;


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

    public Command parse(String fullCommand) throws DukeException {
        List<String> splitInput = new ArrayList<String>(
                Arrays.asList(fullCommand.split(" ")));
        String inputTask = splitInput.get(0);
        if (inputTask.equalsIgnoreCase("addLocker")) {
            return new AddLockerCommandParser().parse(fullCommand);
        } else if (fullCommand.equalsIgnoreCase("bye")) {
            return new ByeCommandParser().parse();
        } else if (fullCommand.equalsIgnoreCase("list")) {
            return new ListCommandParser().parse();
        } else if (inputTask.equalsIgnoreCase("addBatch")) {
            return new AddBatchCommandParser().parse(fullCommand);
        } else if (inputTask.equalsIgnoreCase("delete")) {
            return new DeleteLockerCommandParser().parse(fullCommand);
        } else if (inputTask.equalsIgnoreCase("edit")) {
            return new EditLockerCommandParser().parse(fullCommand);
        } else  {
            throw new DukeException(" Invalid command");
        }
    }
}
