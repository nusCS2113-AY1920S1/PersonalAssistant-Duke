package duke.util;

import duke.command.*;
import duke.exceptions.DukeCommandException;
import duke.exceptions.DukeEmptyCommandException;
import duke.exceptions.DukeInvalidTimeException;
import duke.tasks.Deadline;
import duke.tasks.Events;
import duke.tasks.Task;
import duke.tasks.Todo;

public class Parser {

    /**
     * Checks if the index input when using the done command
     * is a valid index within the active taskList.
     * Returns DoneCommand which indicates which task index
     * is to be marked as completed.
     * @param input Partially parsed string input by user.
     * @return DoneCommand indicating which task to be marked as completed.
     * @throws DukeEmptyCommandException when the index cannot be parsed to an integer.
     */
    private static Command checkValidDoneIndex(String input) throws DukeEmptyCommandException {
        String[] hold = input.split(" ");
        int test = hold.length;
        int index = Integer.parseInt(hold[1]);
        if (test > 2) {
            throw new DukeEmptyCommandException();
        } else {
            return new DoneCommand(index);
        }
    }

    private static Command checkValidRescheduleIndex(String input) throws DukeCommandException {
        String[] hold = input.replaceAll(" {2,}", " ").split(" ");
        int test = hold.length;
        if (test > 3) {
            throw new DukeCommandException();
        }
        try {
            return new RescheduleCommand(Integer.parseInt(hold[1]), hold[2]);
        } catch (NumberFormatException ex) {
            throw new DukeCommandException();
        }
    }

    /**
     * Checks valid inputs for task adding command.
     * @param inputs Partially parsed user input for adding command.
     * @return String array of adding command parsed by keywords.
     * @throws DukeEmptyCommandException when user inputs failed input parsing.
     */
    private static String[] testRegex(String inputs) throws DukeEmptyCommandException {
        if (inputs.startsWith("deadline")) {
            if (!inputs.contains("/by")) {
                throw new DukeEmptyCommandException();
            }
        }
        if (inputs.startsWith("event")) {
            if (!inputs.contains("/at")) {
                throw new DukeEmptyCommandException();
            }
        }
        if (inputs.equals("todo")) {
            throw new DukeEmptyCommandException();
        }
        String[] res = inputs.split("/", 2);
        if (res.length == 0) {
            throw new DukeEmptyCommandException();
        }
        return res;
    }

    private static String[] testRegex(String inputs, String keyword) throws DukeEmptyCommandException {
        if (keyword.equals("todo") && inputs.equals("todo")) {
            throw new DukeEmptyCommandException();
        } else if (keyword.equals("deadline") && inputs.startsWith("deadline ") && !inputs.contains("/by")) {
            throw new DukeEmptyCommandException();
        } else if (keyword.equals("event") && inputs.startsWith("event ") && !inputs.contains("/at")) {
            throw new DukeEmptyCommandException();
        } else {
            String[] res = inputs.split("/", 2);
            if (res.length == 0) {
                throw new DukeEmptyCommandException();
            }
            return res;
        }
    }

    /**
     * Checks user input for deletion command,
     * and returns deletion command with the intended index.
     * @param input User input to be parsed for deletion command.
     * @return DeleteCommand with the task index to be deleted.
     * @throws DukeEmptyCommandException when user inputs delete command without any index.
     * @throws DukeCommandException when user inputs delete command with an invalid index.
     */
    private static Command deleteTask(String input) throws DukeEmptyCommandException, DukeCommandException {
        String[] split = input.split(" ", 2);
        int index;
        if (split[split.length - 1].equals("")) {
            throw new DukeEmptyCommandException();
        }
        try {
            index = Integer.parseInt(split[split.length - 1]);
        } catch (NumberFormatException e) {
            throw new DukeCommandException();
        }
        return new DeleteCommand(index);
    }

    /**
     * Split user input by spaces, and returns the last string in the array.
     * @param input User when when find command is detected.
     * @return FindCommand initialized with the String to search for in taskList.
     */
    private static Command parseFind(String input) {
        String[] split = input.split(" ", 2);
        return new FindCommand(split[split.length - 1]);
    }

    private static String[] parseAdding(String input, String keyword)
            throws DukeEmptyCommandException {
        String[] split = testRegex(input, keyword);
        if (!split[0].equals("")) {
            throw new DukeEmptyCommandException();
        }
        split[split.length - 1] = split[split.length - 1].trim();
        if (keyword.equals("deadline")) {
            split[split.length - 1] = split[split.length - 1].replaceFirst("by ", "");
        } else if (keyword.equals("event")) {
            split[split.length - 1] = split[split.length - 1].replaceFirst("at ", "");
        }
        return split;
    }



    /**
     * Main parser for user commands, checking for any invalid input
     * placed and empty command placed. Returns the specified command
     * for each specified command.
     * @param input Raw user string read by Ui object.
     * @return Specified command object based on user input.
     * @throws DukeCommandException when the user inputs an invalid command.
     * @throws DukeEmptyCommandException when the user inputs and empty command.
     */
    public static Command parse(String input)
            throws DukeCommandException, DukeEmptyCommandException, DukeInvalidTimeException {
        //Checks every input for keyword command
        input = input.trim();
        if (input.startsWith("todo ")) {
            String[] temp = input.split("todo ");
            String [] split = testRegex(temp[temp.length - 1]);
            if (!temp[0].equals("")) {
                throw new DukeCommandException();
            }
            Task hold = new Todo(split);
            return new AddCommand(hold);
        } else if (input.startsWith("event ")) {
            String[] temp = input.split("event");
            String [] split = testRegex(temp[temp.length - 1]);
            if (!temp[0].equals("")) {
                throw new DukeCommandException();
            }
            split[split.length - 1] = split[split.length - 1].trim();
            split[split.length - 1] = split[split.length - 1].replaceFirst("at ", "");
            Task hold = new Events(split);
            return new AddCommand(hold);
        } else if (input.startsWith("deadline ")) {
            String[] temp = input.split("deadline");
            String [] split = testRegex(temp[temp.length - 1]);
            if (!temp[0].equals("")) {
                throw new DukeCommandException();
            }
            split[split.length - 1] = split[split.length - 1].trim();
            split[split.length - 1] = split[split.length - 1].replaceFirst("by ", "");
            Task hold = new Deadline(split);
            return new AddCommand(hold);
        } else if (input.equals("bye")) {
            return new ByeCommand();
        } else if (input.startsWith("done ")) {
            return checkValidDoneIndex(input);
        } else if (input.startsWith("delete ")) {
            return deleteTask(input);
        } else if (input.equals("list")) {
            return new ListCommand();
        } else if (input.startsWith("find ")) {
            return parseFind(input);
        } else if (input.startsWith("reschedule ")) {
            return checkValidRescheduleIndex(input);
        } else {
            //throws invalid command exception when user inputs non-keywords
            throw new DukeCommandException();
        }
    }
}
