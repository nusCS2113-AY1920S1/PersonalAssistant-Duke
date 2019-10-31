package eggventory.logic.commands;

import java.util.ArrayList;
import javafx.util.Pair;

//@@author Raghav-B
public class CommandDictionary {

    // ArrayList of Pairs of format (commands, arguments)
    private ArrayList<Pair<String, String>> commandDict = new ArrayList<>();

    /**
     * Contains all possible valid commands that can be used by the user. Can add new commands here
     * and they will be automatically used by the predictive search.
     */
    public CommandDictionary() {
        // Add Commands
        commandDict.add(new Pair<>("add stock", "<Stock Type> <Stock Code> <Quantity> <Description>"));
        commandDict.add(new Pair<>("add stocktype", "<Stock Type>"));
        commandDict.add(new Pair<>("add person", "<Matric No.> <Name>"));
        commandDict.add(new Pair<>("add template", "<Template Name>"));

        // Delete Commands
        commandDict.add(new Pair<>("delete stock", "<Stock Code>"));
        commandDict.add(new Pair<>("delete stocktype", "<Stock Type>"));
        commandDict.add(new Pair<>("delete person", "<Matric No.>"));
        commandDict.add(new Pair<>("delete template", "<Template Name>"));

        // Edit Commands
        commandDict.add(new Pair<>("edit stock", "<Stock Code> <Property> <New Value>"));
        commandDict.add(new Pair<>("edit person", "<Property> <New Value>"));

        // List Commands
        commandDict.add(new Pair<>("list stock", null));
        commandDict.add(new Pair<>("list stocktype", "all"));
        commandDict.add(new Pair<>("list stocktype", "<Stock Type>"));
        commandDict.add(new Pair<>("list loan", null));
        commandDict.add(new Pair<>("list template", null));
        commandDict.add(new Pair<>("list lost", null));
        commandDict.add(new Pair<>("list person", null));

        // Find Commands
        commandDict.add(new Pair<>("find stock", "<Query>"));
        commandDict.add(new Pair<>("find stocktype", "<Query>"));

        // Loan Commands
        commandDict.add(new Pair<>("loan add", "<Matric No.> <Stock Code> <Quantity>"));
        commandDict.add(new Pair<>("loan add", "<Template Name>"));
        commandDict.add(new Pair<>("loan returned", "<Matric No.> <Stock Code> <Quantity>"));
        commandDict.add(new Pair<>("loan returned", "<Matric No.> <Template Name>"));

        // Lost Commands
        commandDict.add(new Pair<>("lost", "<Stock Code> <Quantity>"));

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
    public ArrayList<String> searchDictCommands(String query) {
        ArrayList<String> curSearch = new ArrayList<>();

        // Iterating through arguments of the same command
        for (int i = 0; i < commandDict.size(); i++) {
            // Checking if current Dictionary key matches what
            // the user has entered so far.
            if (commandDict.get(i).getKey().startsWith(query)) {
                //System.out.println("Detected");
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
    public ArrayList<String> searchDictArguments(String command) {
        ArrayList<String> curSearch = new ArrayList<>();

        for (int i = 0; i < commandDict.size(); i++) {
            if (commandDict.get(i).getKey().equals(command)) {
                curSearch.add(commandDict.get(i).getValue());
            }
        }

        // Returns an empty ArrayList if no match has been found.
        return curSearch;
    }
}
