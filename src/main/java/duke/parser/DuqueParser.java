package duke.parser;

import duke.command.*;
import duke.exception.DukeException;
import duke.storage.UndoStack;

import java.util.Optional;

/**
 * duke.parser.Parser class that deals with making sense of user commands
 */
public class DuqueParser {
    /**
     * Returns a duke.command.Command object which will be specific to the keywords given in the user command
     * Segregates the different categories of user commands
     * Allows them to be dealt with specifically like how the should be with less confusion
     *
     * @param fullCommand the entire user command
     * @return duke.command.Command the class duke.command.Command will execute the user command
     * @throws DukeException in case of user input errors which duke.parser.Parser cannot recognise,
     *                       the parser will return
     *                       specific error messages depending on the reason of the error
     */
    public static Command parseCommand(String fullCommand, UndoStack undoStack) throws DukeException {
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
        String keyword = OffByOneChecker.offByOne(fcArray[0]);
        keyword = StartsWithChecker.checkStartsWithAnyCommand(keyword);

        switch (keyword) {
        case "bye":
            return new ExitCommand();
        case "help":
            return new HelpCommand();
        case "list":
            if (1 == fcArray.length) {
                return new ListCommand(filter);
            }
            return new ListCommand(fcArray[1], filter);
        case "delete":
            if (fcArray.length == 1) {
                throw new DukeException("☹ OOPS!!! The description of delete cannot be empty.");
            }
            return new DeleteCommand(filter, fcArray[1]);
        case "find":
            if (fcArray.length == 1) {
                throw new DukeException("☹ OOPS!!! The description of find cannot be empty.");
            }
            return new FindCommand(fcArray[1], filter);
        case "random":
            if (fcArray.length == 1) {
                return new RandomCommand(filter);
            }
            return new RandomCommand(fcArray[1], filter);
        case "done":
            if (fcArray.length == 1) {
                throw new DukeException("☹ OOPS!!! The description of done cannot be empty.");
            }
            return new DoneCommand(filter, fcArray[1]);
        case "edit":
            if (fcArray.length == 1) {
                throw new DukeException("☹ OOPS!!! The description of edit cannot be empty.");
            }
            return new EditCommandParser().parse(filter, fcArray[1]);
        case "task":
        case "event":
            if (fcArray.length == 1) {
                throw new DukeException("☹ OOPS!!! The description of " + keyword + " cannot be empty.");
            }
            return new AddCommandParser().parse(filter, fullCommand);
        case "pomo":
            if (fcArray.length == 1) {
                throw new DukeException("☹ OOPS!!! Please specify which pomodoro timer you would like to start!");
            }
            return new PomodoroCommand(fcArray[1]);
        case "autoassign":
            if (fcArray.length == 1) {
                throw new DukeException("☹ OOPS!!! Please specify which task to auto assign!");
            }
            return new AutoAssignCommand(fcArray[1]);
        case "undo":
            if (fcArray.length != 1) {
                throw new DukeException("☹ OOPS!!! There should not be any description for undo!");
            }
            return new UndoCommand(undoStack);
        default:
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
