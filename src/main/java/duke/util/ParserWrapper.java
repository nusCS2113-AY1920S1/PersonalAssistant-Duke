package duke.util;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import duke.command.AddCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.ListCommand;
import duke.command.ScheduleCommand;
import duke.exceptions.ModCommandException;
import duke.exceptions.ModException;
import duke.exceptions.ModInvalidTimeException;
import duke.modules.Deadline;
import duke.modules.DoWithin;
import duke.modules.Events;
import duke.modules.FixedDurationTasks;
import duke.modules.RecurringTask;
import duke.modules.Task;
import duke.modules.Todo;


public class ParserWrapper {

    private NattyWrapper natty;

    /**
     * Constructor for parser wrapper class.
     */
    public ParserWrapper() {
        natty = new NattyWrapper();
    }

    /**
     * Formats data parsed by natty into the right format for our use case.
     * @param date User input for data parameter.
     * @return LocalDateTime formatted in dd-MM-yyyy [HH:mm].
     * @throws ModInvalidTimeException when string date cannot be parsed by natty.
     */
    private String formatInputToStringDate(String date) throws ModInvalidTimeException {
        return natty.dateToLocalDateTime(date).format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    /**
     * Main parser for user commands, checking for any invalid input
     * placed and empty command placed. Returns the specified command
     * class for each valid input.
     * @param input User input.
     * @return Command class based on user input.
     * @throws ModException error based on user input.
     */
    public Command parse(String input) throws ModException {
        // Checks every input for keywords
        input = input.trim();
        if (input.startsWith("todo ")) {
            String[] split = DukeParser.parseAdding(input, "todo");
            Task hold = new Todo(split);
            return new AddCommand(hold);
        } else if (input.startsWith("event ")) {
            String[] split = DukeParser.parseAdding(input, "event");
            split[split.length - 1] = formatInputToStringDate(split[split.length - 1]);
            Task hold = new Events(split);
            return new AddCommand(hold);
        } else if (input.startsWith("deadline ")) {
            String[] split = DukeParser.parseAdding(input, "deadline");
            split[split.length - 1] = formatInputToStringDate(split[split.length - 1]);
            Task hold = new Deadline(split);
            return new AddCommand(hold);
        } else if (input.startsWith("recurring ")) {
            String[] split = DukeParser.parseAdding(input, "recurring");
            Task hold = new RecurringTask(split);
            return new AddCommand(hold);
        } else if (input.startsWith("fixedDuration")) {
            String[] split = DukeParser.parseAdding(input, "fixedDuration");
            split[split.length - 1] = formatInputToStringDate(split[split.length - 1]);
            Task hold = new FixedDurationTasks(split);
            return new AddCommand(hold);
        } else if (input.startsWith("doWithin ")) {
            LinkedHashMap<String, String> args = DukeParser.parse(input, true, true);
            DukeParser.checkContainRequiredArguments(args, "/begin", "/end");
            String nattyBegin = formatInputToStringDate(args.get("/begin"));
            String nattyEnd = formatInputToStringDate(args.get("/end"));
            args.put("/begin", nattyBegin);
            args.put("/end", nattyEnd);
            Task hold = new DoWithin(args.get("description"), args.get("/begin"), args.get("/end"));
            return new AddCommand(hold);
        } else if (input.equals("bye")) {
            return new ByeCommand();
        } else if (input.startsWith("done ")) {
            return DukeParser.checkValidDoneIndex(input);
        } else if (input.startsWith("delete ")) {
            return DukeParser.deleteTask(input);
        } else if (input.equals("list")) {
            return new ListCommand();
        } else if (input.startsWith("find ")) {
            return DukeParser.parseFind(input);
        } else if (input.startsWith("reschedule ")) {
            return DukeParser.checkValidRescheduleIndex(input);
        } else if (input.startsWith("schedule ")) {
            return new ScheduleCommand(input);
        } else {
            //throws invalid command exception when user inputs non-keywords
            throw new ModCommandException();
        }
    }



}
