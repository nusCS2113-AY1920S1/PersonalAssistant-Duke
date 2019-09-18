package duke.parser;

import duke.command.AddDeadlineCommand;
import duke.command.AddDoAfterCommand;
import duke.command.AddEventCommand;
import duke.command.AddFixedDurationCommand;
import duke.command.AddRecurringCommand;
import duke.command.AddToDoCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.exceptions.DukeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    /**
     * This function just parses the fullCommand into different tasks/commands.
     * @param fullCommand stores the command entered by the user.
     * @return objects of type Command depending on the command given by the user.
     * @throws DukeException when the command given is invalid.
     */
    public static Command parse(String fullCommand)throws DukeException {
        List<String> splitInput = new ArrayList<String>(
                Arrays.asList(fullCommand.split(" ")));
        String inputTask = splitInput.get(0);
        if (fullCommand.equals("list")) {
            return new ListCommand();
        } else if (fullCommand.equals("bye")) {
            return new ByeCommand();
        } else if (inputTask.equals("done")) {
            return new DoneCommand(splitInput);
        } else if (inputTask.equals("todo")) {
            return new AddToDoCommand(fullCommand);
        } else if (inputTask.equals("deadline")) {
            return new AddDeadlineCommand(splitInput);
        } else if (inputTask.equals("event")) {
            return new AddEventCommand(splitInput);
        } else if (inputTask.equals("do-after")) {
            return new AddDoAfterCommand(splitInput);
        } else if (inputTask.equals("fixed")) {
            return new AddFixedDurationCommand(splitInput);
        } else if (inputTask.equals("recursive")) {
            return new AddRecurringCommand(splitInput);
        } else if (inputTask.equals("delete")) {
            return new DeleteCommand(splitInput);
        } else if (inputTask.equals("find")) {
            return new FindCommand(splitInput);
        } else {
            throw new DukeException(" Please enter a valid command");
        }
    }
}