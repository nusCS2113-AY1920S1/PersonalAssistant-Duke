package duke.parser;

import duke.command.*;
import duke.exception.DukeException;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Optional<String> filter = Optional.empty();
        fullCommand = fullCommand.trim();
        if (fullCommand.charAt(0) == '-') {
            if (fullCommand.length() == 1 || !fullCommand.contains(" ")) {
                throw new DukeException("Sorry I don't understand what you mean.");
            }
            filter = Optional.of(fullCommand.substring(1, fullCommand.indexOf(' ')));
            fullCommand = fullCommand.substring(fullCommand.indexOf(' ') + 1);
        }
        String[] fcArray = fullCommand.split(" ", 2);
        String keyword = fcArray[0];
        keyword = OffByOneChecker.offByOne(keyword);

        switch (keyword) {
            case "bye":
                return new ExitCommand();
            case "help":
                return new HelpCommand();
            case "list":
                if (fcArray.length == 1) {
                    return new ListCommand(filter);
                }
                return new ListCommand(fcArray[1], filter);
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
            case "edit":
                if (fcArray.length == 1) {
                    throw new DukeException("☹ OOPS!!! The description of edit cannot be empty.");
                }
                return new EditCommand(filter, fcArray[1]);
            case "todo":
            case "deadline":
            case "event":
                if (fcArray.length == 1) {
                    throw new DukeException("☹ OOPS!!! The description of " + keyword + " cannot be empty.");
                }
                return new AddCommand(fcArray[1], keyword, filter);
            default:
                throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
