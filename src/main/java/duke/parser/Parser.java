package duke.parser;

import duke.command.*;
import duke.exception.DukeException;
import duke.task.Recurring;

/**
 * duke.parser.Parser class that deals with making sense of user commands
 */
public class Parser {
    /**
     * Returns a duke.command.Command object which will be specific to the keywords given in the user command
     * Segregates the different categories of user commands
     * Allows them to be dealt with specifically like how the should be with less confusion
     *
     * @param fullCommand the entire user command
     * @return duke.command.Command the class duke.command.Command will execute the user command
     * @throws DukeException in case of user input errors which duke.parser.Parser cannot recognise, the parser will return
     *                       specific error messages depending on the reason of the error
     */
    public static Command parse(String fullCommand) throws DukeException {
        String[] fcArray = fullCommand.split(" ", 2);
        String keyword = fcArray[0];

        switch (keyword) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "delete":
                if (fcArray.length == 1) {
                    throw new DukeException("☹ OOPS!!! The description of delete cannot be empty.");
                }
                return new DeleteCommand(fcArray[1]);
            case "find":
                if (fcArray.length == 1) {
                    throw new DukeException("☹ OOPS!!! The description of find cannot be empty.");
                }
                return new FindCommand(fcArray[1]);
            case "done":
                if (fcArray.length == 1) {
                    throw new DukeException("☹ OOPS!!! The description of done cannot be empty.");
                }
                return new DoneCommand(fcArray[1]);
            case "todo":
            case "deadline":
            case "event":
                if (fcArray.length == 1) {
                    throw new DukeException("☹ OOPS!!! The description of " + keyword + " cannot be empty.");
                }
                return new AddCommand(fcArray[1], keyword);
            default:
                throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
