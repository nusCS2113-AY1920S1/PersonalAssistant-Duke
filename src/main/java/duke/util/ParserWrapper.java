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
import duke.tasks.Deadline;
import duke.tasks.DoWithin;
import duke.tasks.Events;
import duke.tasks.FixedDurationTasks;
import duke.tasks.RecurringTask;
import duke.tasks.Task;
import duke.tasks.Todo;


public class ParserWrapper {

    // Testing Natty Wrapper
    private NattyWrapper natty;
    private final String[] dateTasks = {"event", "deadline", "fixedDuration"};

    /**
     * Constructor for parser wrapper class.
     */
    public ParserWrapper() {
        natty = new NattyWrapper();
    }

    private String formatInputToStringDate(String date) throws ModInvalidTimeException {
        return natty.dateToLocalDateTime(date).format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    /**
     * Parsing date arguments.
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
