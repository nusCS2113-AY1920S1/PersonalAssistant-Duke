package duke.util;

import duke.command.AddCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.RescheduleCommand;
import duke.command.ScheduleCommand;
import duke.exceptions.DukeCommandException;
import duke.exceptions.DukeEmptyCommandException;
import duke.exceptions.DukeInvalidTimeException;
import duke.exceptions.DukeInvalidTimePeriodException;
import duke.exceptions.DukeMissingArgumentException;
import duke.exceptions.DukeMultipleValuesForSameArgumentException;
import duke.tasks.Deadline;
import duke.tasks.DoWithin;
import duke.tasks.Events;
import duke.tasks.FixedDurationTasks;
import duke.tasks.RecurringTask;
import duke.tasks.Task;
import duke.tasks.Todo;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DukeParser {

    /**
     * Checks if the index input when using the done command
     * is a valid index within the active taskList.
     * Returns DoneCommand which indicates which task index
     * is to be marked as completed.
     * @param input Partially parsed string input by user.
     * @return DoneCommand indicating which task to be marked as completed.
     * @throws DukeEmptyCommandException when the index cannot be parsed to an integer.
     */
    public static Command checkValidDoneIndex(String input) throws DukeEmptyCommandException {
        String[] hold = input.split(" ");
        int test = hold.length;
        int index = Integer.parseInt(hold[1]);
        if (test > 2) {
            throw new DukeEmptyCommandException();
        } else {
            return new DoneCommand(index);
        }
    }

    /**
     * Checks task index for valid task reschedule.
     * @param input User input.
     * @return Tasked to be reschedule.
     * @throws DukeCommandException When user inputs an invalid command.
     */
    public static Command checkValidRescheduleIndex(String input) throws DukeCommandException {
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
     * Checks user input for required arguments.
     * @param parsedArgs LinkedHashMap of parsed arguments and their values.
     * @param args The specified arguments for command.
     * @throws DukeMissingArgumentException when user inputs command with missing arguments.
     */
    public static void checkContainRequiredArguments(LinkedHashMap<String, String> parsedArgs, String... args)
            throws DukeMissingArgumentException {
        for (String arg: args) {
            if (!parsedArgs.containsKey(arg) || parsedArgs.get(arg).isBlank()) {
                throw new DukeMissingArgumentException(arg);
            }
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
    public static Command deleteTask(String input)
            throws DukeEmptyCommandException,
            DukeCommandException {
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
    public static Command parseFind(String input) {
        String[] split = input.split(" ", 2);
        return new FindCommand(split[split.length - 1]);
    }

    /**
     * Checks valid inputs for task adding command.
     * @param inputs Partially parsed user input for adding command.
     * @param keyword Command keyword related to the task type.
     * @return String array of adding command parsed by keywords.
     * @throws DukeEmptyCommandException when user inputs failed input parsing.
     */
    public static String[] testRegex(String inputs, String keyword) throws DukeEmptyCommandException {
        if (keyword.equals("todo")
                && inputs.equals("todo")) {
            throw new DukeEmptyCommandException();
        } else if (keyword.equals("deadline")
                && inputs.startsWith("deadline ")
                && !inputs.contains("/by")) {
            throw new DukeEmptyCommandException();
        } else if (keyword.equals("event")
                && inputs.startsWith("event ")
                && !inputs.contains("/at")) {
            throw new DukeEmptyCommandException();
        } else if (keyword.equals("fixedDuration")
                && inputs.startsWith("fixedDuration")
                && !inputs.contains("/needs")) {
            throw new DukeEmptyCommandException();
        } else if (keyword.equals("recurring")
                && inputs.startsWith("recurring ")
                && !inputs.contains("/every")) {
            throw new DukeEmptyCommandException();
        } else {
            String[] res = inputs.split((keyword + " "), 2);
            if (res.length == 0) {
                throw new DukeEmptyCommandException();
            }
            return res;
        }
    }

    /**
     * Helper functions for creating new tasks.
     * @param input Raw user input.
     * @param keyword Command keyword based user input.
     * @return String array containing parsed user input.
     * @throws DukeEmptyCommandException When user inputs an empty command.
     */
    public static String[] parseAdding(String input, String keyword) throws DukeEmptyCommandException {
        String[] split = testRegex(input, keyword);
        if (!split[0].equals("")) {
            throw new DukeEmptyCommandException();
        }
        split[split.length - 1] = split[split.length - 1].trim();
        switch (keyword) {
            case "todo":
                break;
            case "deadline":
                split[split.length - 1] = split[split.length - 1].replaceFirst("by ", "");
                break;
            case "event":
                split[split.length - 1] = split[split.length - 1].replaceFirst("at ", "");
                break;
            case "fixedDuration":
                split[split.length - 1] = split[split.length - 1].replaceFirst("needs ", "");
                break;
            case "recurring":
                split[split.length - 1] = split[split.length - 1].replaceFirst("every ", "");
                break;
            default:
                throw new DukeEmptyCommandException();
        }
        String[] ret = Arrays.copyOfRange(split, 1, split.length);
        if (ret.length == 1) {
            return ret[0].split("/", 2);
        }
        return ret;
    }

    /**
     * Main parser for user commands, checking for any invalid input
     * placed and empty command placed. Returns the specified command
     * class for each valid input.
     * @param input Raw user string read by Ui object.
     * @return Specified command object based on user input.
     * @throws DukeCommandException when the user inputs an invalid command.
     * @throws DukeEmptyCommandException when the user inputs and empty command.
     */
    public static Command parse(String input)
            throws DukeCommandException, DukeEmptyCommandException,
            DukeInvalidTimeException, DukeMultipleValuesForSameArgumentException,
            DukeMissingArgumentException, DukeInvalidTimePeriodException {
        // Checks every input for keywords
        input = input.trim();
        if (input.startsWith("todo ")) {
            String[] split = parseAdding(input, "todo");
            Task hold = new Todo(split);
            return new AddCommand(hold);
        } else if (input.startsWith("event ")) {
            String[] split = parseAdding(input, "event");
            Task hold = new Events(split);
            return new AddCommand(hold);
        } else if (input.startsWith("deadline ")) {
            String[] split = parseAdding(input, "deadline");
            Task hold = new Deadline(split);
            return new AddCommand(hold);
        } else if (input.startsWith("recurring ")) {
            String[] split = parseAdding(input, "recurring");
            Task hold = new RecurringTask(split);
            return new AddCommand(hold);
        } else if (input.startsWith("fixedDuration")) {
            String[] split = parseAdding(input, "fixedDuration");
            Task hold = new FixedDurationTasks(split);
            return new AddCommand(hold);
        } else if (input.startsWith("doWithin ")) {
            LinkedHashMap<String, String> args = parse(input, true, true);
            checkContainRequiredArguments(args, "/begin", "/end");
            Task hold = new DoWithin(args.get("description"), args.get("/begin"), args.get("/end"));
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
        } else if (input.startsWith("schedule ")) {
            return new ScheduleCommand(input);
        } else {
            //throws invalid command exception when user inputs non-keywords
            throw new DukeCommandException();
        }
    }

    /**
     * Returns LinkedHashMap of command and included args.
     * @param command Command input.
     * @param includeCommand Check for included command.
     * @param includeArgs Check for included arguments.
     * @return LinkedHashMap of Command and args to values input by user.
     * @throws DukeMultipleValuesForSameArgumentException When user inputs too many arguments.
     */
    public static LinkedHashMap<String, String> parse(String command,
                                                      boolean includeCommand,
                                                      boolean includeArgs)
            throws DukeMultipleValuesForSameArgumentException {
        return parse(command, includeCommand, includeArgs, "/", true);
    }

    /**
     * Overloaded function which returns a hash map.
     * @param command Command desired.
     * @param includeCommand Command to be executed.
     * @param includeArgs Included parameters for command.
     * @param delimiter user delimiter to split input.
     * @param isTrim boolean result if the input has been trimmed.
     * @return a Linked hash map of the input values.
     * @throws DukeMultipleValuesForSameArgumentException if input contains too many arguments.
     */
    public static LinkedHashMap<String, String> parse(String command,
                                                      boolean includeCommand,
                                                      boolean includeArgs,
                                                      String delimiter,
                                                      boolean isTrim)
            throws DukeMultipleValuesForSameArgumentException {
        LinkedHashMap<String, String> ret = new LinkedHashMap<>();
        String commandClean = command.trim();
        int endCommandIndex = commandClean.indexOf(" ");
        if (endCommandIndex == -1) {
            endCommandIndex = commandClean.length();
            if (includeCommand) {
                ret.put("command", commandClean.substring(0, endCommandIndex));
            }
            return ret;
        }
        if (includeCommand) {
            ret.put("command", commandClean.substring(0, endCommandIndex++));
        }
        if (includeArgs) {
            commandClean = commandClean.substring(endCommandIndex).trim();
            String regex = " ?" + delimiter + "[a-zA-Z]+ ?";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(commandClean);
            int currIndex = 0;
            String currArgument = "description";
            while (matcher.find()) {
                if (ret.containsKey(currArgument)) {
                    throw new DukeMultipleValuesForSameArgumentException();
                }
                if (isTrim) {
                    ret.put(currArgument, commandClean.substring(currIndex, matcher.start()).trim());
                    currArgument = matcher.group().trim();
                } else {
                    ret.put(currArgument, commandClean.substring(currIndex, matcher.start()));
                    currArgument = matcher.group();
                }
                currIndex = matcher.end();
            }
            ret.put(currArgument, commandClean.substring(currIndex));
        }
        return ret;
    }
}
