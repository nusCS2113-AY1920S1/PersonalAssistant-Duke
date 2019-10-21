package planner.util;


import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import planner.command.AddCommand;
import planner.command.ByeCommand;
import planner.command.CapCommand;
import planner.command.Command;
import planner.command.ListCommand;
import planner.command.ReportCommand;
import planner.command.ScheduleCommand;
import planner.command.logic.CoreModuleReportCommand;
import planner.command.logic.EndCommand;
import planner.command.logic.GeneralModuleReportCommand;
import planner.command.logic.ModuleCommand;
import planner.command.logic.UnrestrictedModuleReportCommand;
import planner.exceptions.ModCommandException;
import planner.exceptions.ModException;
import planner.exceptions.ModInvalidTimeException;
import planner.modules.Cca;
import planner.modules.Deadline;
import planner.modules.DoWithin;
import planner.modules.Events;
import planner.modules.FixedDurationTask;
import planner.modules.RecurringTask;
import planner.modules.Task;
import planner.modules.Todo;


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
        return natty
                .dateToLocalDateTime(date)
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    private String[] splitFirstSpace(String input) {
        return input.split(" ", 2);
    }

    /**
     * Switching parser to module parser from duke.
     * @param input User string input.
     * @param isDuke selector for duke parser or mod parse.
     * @return Command class based on user input
     * @throws ModException If user inputs strings which are invalid.
     */
    public ModuleCommand parse(String input, boolean isDuke) throws ModException {
        if (isDuke) {
            return new EndCommand();
        }
        String[] hold = splitFirstSpace(input);
        //TODO: update the parsing below with a more robust Argsj4 library
        switch (hold[0]) {
            //case "add": {
            //    return new SearchThenAddCommand(hold[hold.length - 1]);
            //}
            //case "show": {
            //    return new ShowCommand();
            //}
            //case "bye": {
            //    return new EndCommand();
            //}
            //case "remove": {
            //    return new RemoveCommand(Integer.parseInt(hold[hold.length - 1]));
            //}
            case "cap": {
                return new CapCommand(input);
            }
            //case "print": {
            //    return new SortCommand(hold[hold.length - 1]);
            //}
            case "report": {
                switch (hold[1]) {
                    case ("core"): {
                        return new CoreModuleReportCommand();
                    }
                    case ("ge"): {
                        return new GeneralModuleReportCommand();
                    }
                    case ("ue"): {
                        return new UnrestrictedModuleReportCommand();
                    }
                    default: {
                        throw new ModCommandException();
                    }
                }
            }
            default: {
                throw new ModCommandException();
            }
        }
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
        LinkedHashMap<String, String> args;
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
            args = DukeParser.parse(input, false, true);
            RecurringTask hold = new RecurringTask(
                    args.get("description"),
                    args.get("/days"),
                    args.get("/hours"),
                    args.get("/minutes"),
                    args.get("/seconds"));
            return new AddCommand(hold);
        } else if (input.startsWith("fixedDuration ")) {
            args = DukeParser.parse(input, false, true);
            FixedDurationTask hold = new FixedDurationTask(
                    args.get("description"),
                    args.get("/days"),
                    args.get("/hours"),
                    args.get("/minutes"),
                    args.get("/seconds"));
            return new AddCommand(hold);
        } else if (input.startsWith("doWithin ")) {
            args = DukeParser.parse(input, false, true);
            DukeParser.checkContainRequiredArguments(args, "/begin", "/end");
            this.parseDateTime(args, "/begin", "/end");
            Task hold = new DoWithin(args.get("description"), args.get("/begin"), args.get("/end"));
            return new AddCommand(hold);
        } else if (input.startsWith("cca ")) {
            args = DukeParser.parse(input, false, true);
            DukeParser.checkContainRequiredArguments(args, "/begin", "/end", "/day");
            this.parseDateTime(args, "/begin", "/end");
            Task hold = new Cca(args.get("description"), args.get("/begin"), args.get("/end"), args.get("/day"));
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
        } else if (input.startsWith("report")) {
            return new ReportCommand();
        } else {
            //throws invalid command exception when user inputs non-keywords
            throw new ModCommandException();
        }
    }

    /**
     * Parse multiple dateTime arguments.
     * @param args input argument map
     * @param dateTimeArgs dateTime arguments to parse
     * @throws ModInvalidTimeException when input dateTime argument is invalid
     */
    private void parseDateTime(LinkedHashMap<String, String> args, String... dateTimeArgs)
            throws ModInvalidTimeException {
        for (String dateTimeArg: dateTimeArgs) {
            if (args.containsKey(dateTimeArg)) {
                args.put(dateTimeArg, this.formatInputToStringDate(args.get(dateTimeArg)));
            }
        }
    }


}
