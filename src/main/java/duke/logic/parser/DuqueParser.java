package duke.logic.parser;

import java.util.Optional;

import duke.exception.DukeException;
import duke.logic.command.AutoAssignCommand;
import duke.logic.command.Command;
import duke.logic.command.DeleteCommand;
import duke.logic.command.DoneCommand;
import duke.logic.command.ExitCommand;
import duke.logic.command.FindCommand;
import duke.logic.command.HelpCommand;
import duke.logic.command.ListCommand;
import duke.logic.command.PomodoroCommand;
import duke.logic.command.RandomCommand;
import duke.logic.command.UndoCommand;
import duke.storage.UndoStack;

/**
 * duke.logic.parser.Parser class that deals with making sense of user commands
 */
public class DuqueParser {
    /**
     * Returns a duke.logic.parser.command.Command object which will be specific to the keywords given in the user command
     * Segregates the different categories of user commands
     * Allows them to be dealt with specifically like how the should be with less confusion
     *
     * @param fullCommand the entire user command
     * @return duke.logic.parser.command.Command the class duke.logic.parser.command.Command will execute the user command
     * @throws DukeException in case of user input errors which duke.logic.parser.Parser cannot recognise,
     *                       the parser will return
     *                       specific error messages depending on the reason of the error
     */
    public static Command parseCommand(String fullCommand, UndoStack undoStack) throws DukeException {
        Optional<String> filter = Optional.empty();
        fullCommand = fullCommand.trim();
        if (fullCommand.equals("")) {
            throw new DukeException("Please type something for me to do!");
        }
        fullCommand = fullCommand.replaceAll("\t", " ");
        fullCommand = fullCommand.replaceAll("( )+", " ");
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
            if (1 == fcArray.length) {
                return new ExitCommand();
            }
            System.out.println("I don't like goodbyes with strings attached....");
            return new ExitCommand();
        case "help":
            if (1 == fcArray.length) {
                return new HelpCommand();
            }
            System.out.println("You don't need anything but help but okay....");
            return new HelpCommand();
            //update with more detailed help command
        case "list":
            if (1 == fcArray.length) {
                return new ListCommand(filter);
            }
            return new ListCommand(fcArray[1], filter);
        case "delete":
            if (fcArray.length == 1) {
                throw new DukeException("Please input an index of the task to be deleted!");
            }
            return new DeleteCommand(filter, fcArray[1]);
        case "find":
            if (fcArray.length == 1) {
                throw new DukeException("OOPS!!! The description of find cannot be empty.");
            }
            return new FindCommand(fcArray[1], filter);
        case "random":
            if (fcArray.length == 1) {
                return new RandomCommand(filter);
            }
            return new RandomCommand(fcArray[1], filter);
        case "done":
            if (fcArray.length == 1) {
                throw new DukeException("OOPS!!! The description of done cannot be empty.");
            }
            return new DoneCommand(filter, fcArray[1]);
        case "edit":
            if (fcArray.length == 1) {
                throw new DukeException("OOPS!!! The description of edit cannot be empty.");
            }
            return new EditCommandParser().parse(filter, fcArray[1]);
        case "task":
        case "event":
            if (fcArray.length == 1) {
                throw new DukeException("OOPS!!! The description of " + keyword + " cannot be empty.");
            }
            return new AddCommandParser().parse(filter, fullCommand);
        case "pomo":
            if (fcArray.length == 1) {
                throw new DukeException("OOPS!!! Please specify which pomodoro timer you would like to start!");
            }
            return new PomodoroCommand(filter, fcArray[1]);
        case "autoassign":
            if (fcArray.length == 1) {
                throw new DukeException("OOPS!!! Please specify which task to auto assign!");
            }
            return new AutoAssignCommand(fcArray[1]);
        case "undo":
            if (fcArray.length != 1) {
                throw new DukeException("OOPS!!! There should not be any description for undo!");
            }
            return new UndoCommand(undoStack);
        default:
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means.");
        }
    }
}
