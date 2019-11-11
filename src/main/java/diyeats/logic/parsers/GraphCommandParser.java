package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.GraphCommand;

import java.util.HashMap;

//@@author koushireo

/**
 * Parser class to handle information to CGraphCommand.
 */
public class GraphCommandParser implements ParserInterface<GraphCommand> {

    /**
     * Parses user input and returns a GraphCommand encapsulating the desired data.
     * @param userInputStr String input by user.
     * @return <code>GraphCommand</code> Command object encapsulating the graph information to be executed
     * @throws ProgramException when the user input cannot be parsed
     */
    @Override

    public GraphCommand parse(String userInputStr) {
        int month = 0;
        try {
            InputValidator.validate(userInputStr);
        } catch (ProgramException e) {
            return new GraphCommand(false, e.getMessage());
        }
        HashMap<String, String> lineSplit = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        if (!lineSplit.containsKey("month")) {
            return new GraphCommand(false, "Please input the month using /month.");
        }
        try {
            month = Integer.parseInt(lineSplit.get("month"));
        } catch (NumberFormatException e) {
            return new GraphCommand(false, "Please input a valid number for month");
        }
        if (month < 1 || month > 12) {
            return new GraphCommand(false, "Month can only be between 1 to 12");
        }
        int year = 0;
        if (!lineSplit.containsKey("year")) {
            return new GraphCommand(false, "Please input the year using /year.");
        }
        try {
            year = Integer.parseInt(lineSplit.get("year"));
        } catch (NumberFormatException e) {
            return new GraphCommand(false, "Please input a valid number for year");
        }
        if (!lineSplit.containsKey("type")) {
            return new GraphCommand(false, "Please input the type using /type.");
        }
        String type = lineSplit.get("type");
        return new GraphCommand(month, year, type);
    }
}

