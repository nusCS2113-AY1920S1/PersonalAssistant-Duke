package optix.util;

import optix.commands.ByeCommand;
import optix.commands.Command;
import optix.commands.HelpCommand;
import optix.commands.parser.AddAliasCommand;
import optix.commands.parser.ListAliasCommand;
import optix.commands.parser.RemoveAliasCommand;
import optix.commands.parser.ResetAliasCommand;
import optix.commands.seats.ReassignSeatCommand;

import optix.commands.seats.SellSeatCommand;
import optix.commands.seats.ViewSeatsCommand;
import optix.commands.shows.AddCommand;
import optix.commands.shows.DeleteCommand;
import optix.commands.shows.EditCommand;
import optix.commands.shows.ListCommand;
import optix.commands.shows.ListDateCommand;
import optix.commands.shows.ListShowCommand;
import optix.commands.shows.PostponeCommand;
import optix.commands.shows.ViewMonthlyCommand;
import optix.commands.shows.ViewProfitCommand;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;


import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Parse input arguments and create a new Command Object.
 */
public class Parser {

    public static HashMap<String, String> commandAliasMap = new HashMap<>();
    private File preferenceFilePath; // the directory where the file is stored
    private File preferenceFile; // the path to the file itself
    // array of all possible command values
    private static String[] commandList = {"bye", "list", "help", "edit", "sell", "view",
        "postpone", "add", "delete"};

    /**
     * Set the path to directory containing the save file for preferences.
     * Set the path to the save file for preferences.
     *
     * @param filePath path to directory containing the save file for preferences.
     */
    public Parser(File filePath) {
        this.preferenceFile = new File(filePath + "\\ParserPreferences.txt");
        this.preferenceFilePath = filePath;
        // load preferences from file
        if (commandAliasMap.isEmpty()) {
            try {
                loadPreferences();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Parse input argument and create a new Command Object based on the first input word.
     *
     * @param fullCommand The entire input argument.
     * @return Command Object based on the first input word.
     * @throws OptixException if the Command word is not recognised by Optix.
     */

    public Command parse(String fullCommand) throws OptixException {
        // add exception for null pointer exception. e.g. postpone
        String[] splitStr = fullCommand.trim().split(" ", 2);
        String aliasName = splitStr[0];
        String commandName = commandAliasMap.getOrDefault(aliasName, aliasName);
        commandName = commandName.toLowerCase().trim(); // is the lower case and trim necessary ?

        if (splitStr.length == 1) {
            switch (commandName) {
            case "bye":
                return new ByeCommand();
            case "list":
                return new ListCommand();
            case "help":
                return new HelpCommand();
            case "reset-alias":
                return new ResetAliasCommand(this.preferenceFilePath);
            case "list-alias":
                return new ListAliasCommand();
            default:
                throw new OptixInvalidCommandException();
            }
        } else if (splitStr.length == 2) {

            // There will definitely be exceptions thrown here. Need to stress test and then categorise
            switch (commandName) {

            case "edit":
                return new EditCommand(splitStr[1]);
            case "sell":
                return new SellSeatCommand(splitStr[1]);
            case "view":
                return new ViewSeatsCommand(splitStr[1]);
            case "postpone":
                return new PostponeCommand(splitStr[1]);
            case "list":
                return parseList(splitStr[1]);
            case "bye":
                return new ByeCommand();

            case "add": // add poto|5/10/2020|20
                return new AddCommand(splitStr[1]);
            case "delete": // e.g. delete SHOW_NAME DATE_1|DATE_2|etc
                return new DeleteCommand(splitStr[1]);
            case "view-profit": //e.g. view-profit lion king|5/5/2020
                return new ViewProfitCommand(splitStr[1]);
            case "view-monthly": //e.g. view-monthly May 2020
                return new ViewMonthlyCommand(splitStr[1]);
            case "help":
                return new HelpCommand(splitStr[1].trim());
            case "add-alias":
                return new AddAliasCommand(splitStr[1], this.preferenceFilePath);
            case "remove-alias":
                return new RemoveAliasCommand(splitStr[1], commandAliasMap);
            default:
                throw new OptixInvalidCommandException();
            }
        } else {
            throw new OptixInvalidCommandException();
        }
    }

    //@@ OungKennedy


    /**
     * Adds a new alias-command pair to commandAliasMap.
     *
     * @param newAlias new alias to add
     * @param command  existing command to be paired to
     * @throws OptixException thrown when the alias-command pair is invalid (refer to below)
     *                        the alias must not be the name of a command.
     *                        the alias must not already be in use. use remove-alias to remove a pair to redirect existing aliases.
     *                        the command to be paired to must exist (refer to commandList for list of existing commands).
     *                        the pipe symbol is a special character- it cannot be used.
     */
    public void addAlias(String newAlias, String command) throws OptixException {
        if (!newAlias.contains("\\|") // pipe symbol not in alias
                && Arrays.asList(commandList).contains(command) // command exists
                && !commandAliasMap.containsKey(newAlias) // new alias is not already in use
                && !Arrays.asList(commandList).contains(newAlias)) { // new alias is not the name of a command
            commandAliasMap.put(newAlias, command);
        } else {
            throw new OptixException("Alias is already in use, or command does not exist.\n");
        }
    }

    //@@ OungKennedy
    private void loadPreferences() throws IOException {
        File filePath = this.preferenceFile;
        // if file does not exist, create a new file and write the default aliases
        if (filePath.createNewFile()) {
            resetPreferences();
            savePreferences();
        } else { // if file exists then load the preferences within
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String aliasPreference;
            while ((aliasPreference = br.readLine()) != null) {
                if (aliasPreference.length() == 0) { // handle empty line
                    continue;
                }
                String[] aliasDetails = aliasPreference.split("\\|");

                String alias = aliasDetails[0];
                String command = aliasDetails[1];
                try {
                    this.addAlias(alias, command);
                } catch (OptixException e) {
                    System.out.println(e.getMessage());
                }
            }
            br.close();
            fr.close();
        }
    }

    //@@ OungKennedy

    /**
     * Writes the contents of commandAliasMap to the file in preferenceFilePath.
     */
    public void savePreferences() throws IOException {
        FileWriter writer = new FileWriter(this.preferenceFile, false);

        for (Map.Entry<String, String> entry : commandAliasMap.entrySet()) {
            String saveString = entry.getKey() + "|" + entry.getValue() + '\n'; // no need to escape. why?
            writer.write(saveString);
        }
        writer.close();
    }

    //@@ OungKennedy

    /**
     * Method to reset preferences to default values.
     */
    public static void resetPreferences() {
        commandAliasMap.clear();
        commandAliasMap.put("b", "bye");
        commandAliasMap.put("l", "list");
        commandAliasMap.put("h", "help");
        commandAliasMap.put("e", "edit");
        commandAliasMap.put("s", "sell");
        commandAliasMap.put("v", "view");
        commandAliasMap.put("p", "postpone");
        commandAliasMap.put("a", "add");
        commandAliasMap.put("d", "delete");
    }

 
    /**
     * Parse the remaining user input to its respective parameters for ListDateCommand or ListShowCommand.
     *
     * @param details The details to create a new ListDateCommand or ListShowCommand Object.
     * @return new ListDateCommand or ListShowCommand Object.
     */
    private static Command parseList(String details) {
        String[] splitStr = details.split(" ");

        if (splitStr.length == 2) {
            try {
                Integer.parseInt(splitStr[1]);
                return new ListDateCommand(details);
            } catch (NumberFormatException e) {
                return new ListShowCommand(details);
            }
        }

        return new ListShowCommand(details);
    }

    private static Command parseReassign(String details) throws OptixInvalidCommandException {
        String[] splitStr = details.trim().split("\\|");
        if (splitStr.length != 4) {
            throw new OptixInvalidCommandException();
        }

        String showName = splitStr[0].trim();
        String showDate = splitStr[1].trim();
        String oldSeat = splitStr[2].trim();
        String newSeat = splitStr[3].trim();

        return new ReassignSeatCommand(showName, showDate, oldSeat, newSeat);
    }
}
