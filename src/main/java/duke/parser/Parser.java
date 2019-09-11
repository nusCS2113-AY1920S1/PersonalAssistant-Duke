package duke.parser;

import duke.command.Command;
import duke.command.ListCommand;
import duke.command.ByeCommand;
import duke.command.AddToDoCommand;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.AddDeadlineCommand;
import duke.command.AddEventCommand;

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