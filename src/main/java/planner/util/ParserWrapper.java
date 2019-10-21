package planner.util;


import planner.command.CapCommand;
import planner.command.logic.CoreModuleReportCommand;
import planner.command.logic.EndCommand;
import planner.command.logic.GeneralModuleReportCommand;
import planner.command.logic.ModuleCommand;
import planner.command.logic.SortCommand;
import planner.command.logic.RemoveModCommand;
import planner.command.logic.SearchThenAddCommand;
import planner.command.logic.ShowModuleCommand;
import planner.command.logic.UnrestrictedModuleReportCommand;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import planner.exceptions.ModCommandException;
import planner.exceptions.ModException;
import planner.exceptions.ModInvalidTimeException;


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
            case "add": {
                return new SearchThenAddCommand(hold[hold.length - 1]);
            }
            case "show": {
                return new ShowModuleCommand();
            }
            case "bye": {
                return new EndCommand();
            }
            case "remove": {
                return new RemoveModCommand(Integer.parseInt(hold[hold.length - 1]));
            }
            case "cap": {
                return new CapCommand(input);
            }
            case "print": {
                return new SortCommand();
            }
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
