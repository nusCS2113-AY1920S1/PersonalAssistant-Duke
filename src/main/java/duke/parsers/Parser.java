package duke.parsers;

import duke.autocorrect.Autocorrect;
import duke.commands.*;
import duke.exceptions.DukeException;
import duke.tasks.Dinner;
import duke.tasks.Breakfast;
import duke.tasks.Item;
import duke.tasks.Lunch;
import duke.tasks.Meal;
import java.util.Calendar;

/**
 * Parser is a public class that help to parse the command that is inputted from the user.
 * And generate the appropriate command with their appropriate arguments
 */
public class Parser {
    private static Calendar currentDate = Calendar.getInstance();

    /**
     * This is the main function that parse the command inputted by the user.
     * @param fullCommand the string the user input in the CLI
     * @return <code>new ExitCommand()</code>
     *         if the user input "bye"
     *         <code>new AddCommand(new Breakfast())</code> if the user input
     *         "breakfast" followed by the description of the meal
     *         <code>new AddCommand(new Lunch()</code> if the user input
     *         "lunch" followed by the description of the meal
     *         <code>new AddCommand(new Dinner()</code> if the user input
     *         "dinner" followed by the description of the meal
     *         <code>new ListCommand()</code> if the user input
     *         list
     *         <code>new MarkDoneCommand(index)</code> if the user input
     *         "done" followed by the index of the meal to be marked completed
     *         <code>new FindCommand(description)</code> if the user input
     *         "find" followed by the string that needs to be added
     *         <code>new DeleteCommand(index) </code> if the sure input
     *         "delete" followed by the index of the task to be deleted
     * @throws DukeException when the command is not recognized or command syntax is invalid
     */
    public static Command parse(String fullCommand, Autocorrect autocorrect) throws DukeException {
        String[] splitCommand = fullCommand.split(" ", 2);
        String command = splitCommand[0];
        autocorrect.setWord(command);
        autocorrect.execute();
        command = autocorrect.getWord();
        String description = "";

        if (splitCommand.length >= 2) {
            description = splitCommand[1];
        }
        if (command.equals("done") || command.equals("breakfast")
                || command.equals("lunch") || command.equals("dinner")
                || command.equals("edit")) {
            if (description.trim().length() == 0) {
                throw new DukeException("\u2639 OOPS!!! The description of a " + command + " cannot be empty.");
            }
        }

        String name;
        String info;
        String date = "";
        int index;
        switch (command) {
            case "bye":
                return new ExitCommand();
            case "breakfast":
                if (description.contains("/")) {
                    name = description.split("/", 2)[0].trim();
                    info = "/" + description.split("/", 2)[1];
                    return new AddCommand(new Breakfast(name, info, autocorrect));
                } else {
                    return new AddCommand(new Breakfast(description, "", autocorrect));
                }
            case "lunch":
                if (description.contains("/")) {
                    name = description.split("/", 2)[0].trim();
                    info = "/" + description.split("/", 2)[1];
                    return new AddCommand(new Lunch(name, info, autocorrect));
                } else {
                    return new AddCommand(new Lunch(description, "", autocorrect));
                }
            case "dinner":
                if (description.contains("/")) {
                    name = description.split("/", 2)[0].trim();
                    info = "/" + description.split("/", 2)[1];
                    return new AddCommand(new Dinner(name, info, autocorrect));
                } else {
                    return new AddCommand(new Dinner(description, "", autocorrect));
                }
            case "add" :
                name = description.split("/", 2)[0].trim();
                info = "/" + description.split("/", 2)[1];
                return new AddItemCommand(new Item(name, info, autocorrect));
            case "list":
                if (splitCommand.length > 1) {
                    return new ListCommand(splitCommand[1]);
                }
                return new ListCommand();
            case "done":
                name = description.split(" /date ", 2)[0];
                if (description.split(" /date ").length > 1) {
                    date = description.split(" /date ")[1];
                    return new MarkDoneCommand(name, date);
                }
            case "find":
                name = description.split(" /date ", 2)[0];
                if (description.split(" /date ").length > 1) {
                    date = description.split(" /date ")[1];
                    return new FindCommand(name, date);
                }
                return new FindCommand(name);
            case "delete":
                if (splitCommand.length > 1) {
                    // user specifies date and index.
                    if (description.split("/date").length >= 2) {
                        String[] splitArgs = description.split("/date", 2);
                        return new DeleteCommand(splitArgs[0], splitArgs[1]);
                    } else {
                        // user only specifies index to delete for current day.
                        return new DeleteCommand(description);
                    }
                }

                throw new DukeException("Please enter index of meal to delete on today's list or "
                        + "date and index of meal to delete");
            case "update":
                return new UpdateWeightCommand(description);
            case "clear":
                if (splitCommand.length > 1) {
                    String[] splitArgs = description.split(" ", 2);
                    if (splitArgs.length >= 2) {
                        return new ClearCommand(splitArgs[0], splitArgs[1]);
                    }
                }
                throw new DukeException("Please enter 2 dates; Start and End dates to clear meals from.");
            case "edit":
                name = description.split("/", 2)[0];
                info = "/" + description.split("/", 2)[1];
                return new EditCommand(new Meal(name, info, autocorrect));
            default:
                throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what " + command + " means :-(");
        }
    }
}
