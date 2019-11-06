package eggventory.logic.commands;

import java.util.ArrayList;
import javafx.util.Pair;

//@@author Raghav-B
public class CommandDictionary {

    // ArrayList of Pairs of format (commands, arguments)
    private static ArrayList<Pair<String, String>> commandDict = new ArrayList<>();

    /**
     * Contains all possible valid commands that can be used by the user. Can add new commands here
     * and they will be automatically used by the predictive search. This is already initialized by
     * InputPredictor, and thus does not need to be initialized once again.
     */
    public CommandDictionary() {
        // Add Commands
        commandDict.add(new Pair<>("add stock", "<StockType> <StockCode> <Quantity> <Description>"));
        commandDict.add(new Pair<>("add stocktype", "<StockType>"));
        commandDict.add(new Pair<>("add loan", "<MatricNo> <StockCode> <Quantity>"));
        commandDict.add(new Pair<>("add stocktype", "<StockType>"));
        commandDict.add(new Pair<>("add person", "<MatricNo> <Name>"));
        commandDict.add(new Pair<>("add template", "<TemplateName> {<StockCode>, <Quantity>}"));

        // Delete Commands
        commandDict.add(new Pair<>("delete stock", "<StockCode>"));
        commandDict.add(new Pair<>("delete stocktype", "<StockType>"));
        commandDict.add(new Pair<>("delete person", "<MatricNo>"));
        commandDict.add(new Pair<>("delete template", "<TemplateName>"));

        // Edit Commands
        commandDict.add(new Pair<>("edit stock", "<StockCode> <Property> <NewValue>"));
        commandDict.add(new Pair<>("edit person", "<Property> <NewValue>"));

        // List Commands
        commandDict.add(new Pair<>("list stock", null));
        commandDict.add(new Pair<>("list stocktype", "all"));
        commandDict.add(new Pair<>("list stocktype", "<StockType>"));
        commandDict.add(new Pair<>("list loan", null));
        commandDict.add(new Pair<>("list template", null));
        commandDict.add(new Pair<>("list template", "<TemplateName>"));
        commandDict.add(new Pair<>("list lost", null));
        commandDict.add(new Pair<>("list person", null));
        commandDict.add(new Pair<>("list person", "<MatricNo>"));

        // Find Commands
        commandDict.add(new Pair<>("find stock", "<Query>"));
        commandDict.add(new Pair<>("find stocktype", "<Query>"));

        // Loan Commands
        commandDict.add(new Pair<>("loan add", "<MatricNo> <StockCode> <Quantity>"));
        commandDict.add(new Pair<>("loan add", "<Template Name>"));
        commandDict.add(new Pair<>("loan returned", "<MatricNo> <StockCode> <Quantity>"));

        // Lost Commands
        commandDict.add(new Pair<>("lost", "<StockCode> <Quantity>"));

        // Bye Commands
        commandDict.add(new Pair<>("bye", null));
    }

    /**
     * Takes in a command query and returns the full command that might match what
     * has been input so far.
     * @param query Input command to search matches for.
     * @return Returns ArrayList of all commands that match.
     *         Returns exactly one
     *         Returns an empty ArrayList if no matches have been found.
     */
    public static ArrayList<String> searchDictCommands(String query) {
        ArrayList<String> curSearch = new ArrayList<>();

        // Iterating through arguments of the same command
        for (int i = 0; i < commandDict.size(); i++) {
            // Checking if current Dictionary key matches what
            // the user has entered so far.
            if (commandDict.get(i).getKey().startsWith(query)) {
                curSearch.add(commandDict.get(i).getKey());
            }
        }

        // Returns an empty ArrayList if no match has been found.
        return curSearch;
    }

    /**
     * Searches for the arguments for a full Command that has been entered.
     * Precondition: Expects command String to be a completely valid command.
     * @param command Valid command String
     * @return ArrayList of all possible arguments that match the entered command.
     *         Returns an empty ArrayList if no matches have been found.
     *         Returns
     */
    public static ArrayList<String> searchDictArguments(String command) {
        ArrayList<String> curSearch = new ArrayList<>();

        for (int i = 0; i < commandDict.size(); i++) {
            if (commandDict.get(i).getKey().equals(command)) {
                curSearch.add(commandDict.get(i).getValue());
            }
        }

        // Returns an empty ArrayList if no match has been found.
        return curSearch;
    }

    /**
     * Gets String with all usages of a particular Command. Used to print messages that
     * handler errors in user input by showing correct format of commands to be entered.
     * @param query Type of command to list use cases for, for example, 'list'.
     * @return Formatted String to be printed by GUI containing proper use cases of command.
     */
    public static String getCommandUsage(String query) {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("\nAppropriate usage of '%s' is as follows:\n", query));

        for (int i = 0; i < commandDict.size(); i++) {

            if (commandDict.get(i).getKey().startsWith(query)) {
                if (commandDict.get(i).getValue() == null) {
                    outputString.append(String.format("- '%s'\n", commandDict.get(i).getKey()));
                } else {
                    outputString.append(String.format("- '%s %s'\n", commandDict.get(i).getKey(),
                            commandDict.get(i).getValue()));
                }

            }
        }

        return outputString.toString();
    }
}
//@@author