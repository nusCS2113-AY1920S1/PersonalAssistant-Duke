package optix.commands.parser;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class RemoveAliasCommand extends Command {
    private String details;
    private HashMap<String, String> commandAliasMap;

    /**
     * Command to remove an existing alias from aliasCommandMap.
     * @param details the details of alias to remove and its command, in an array
     * @param commandAliasMap the command alias map
     */
    public RemoveAliasCommand(String details, HashMap<String, String> commandAliasMap) {
        this.details = details;
        this.commandAliasMap = commandAliasMap;
    }

    public String[] parseDetails(String details) throws OptixInvalidCommandException {
        String[] detailsArray = details.split("\\|",2);
        if (detailsArray.length != 2) {
            throw new OptixInvalidCommandException();
        }
        return detailsArray;
    }

    /**
     * Processes user input to be stored, queried, modified in ShowMap,
     * to show response by program in ui and store existing data in Storage.
     *
     * @param model   The data structure holding all the information.
     * @param ui      The User Interface that reads user input and response to user.
     * @param storage The filepath of txt file which data are being stored.
     */
    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        // parse the string to assign the details and command strings to local variables
        String alias, command;
        try {
            String[] detailsArray = parseDetails(this.details);
            alias = detailsArray[0];
            command = detailsArray[1];
        } catch (OptixInvalidCommandException e) {
            ui.setMessage(e.getMessage());
            return "";
        }

        try {
        // check if the alias exists
            if (!commandAliasMap.containsValue(command) || !commandAliasMap.containsKey(alias)) {
                throw new OptixException("Error removing alias.\n");
            }
            // edit command alias map
            commandAliasMap.remove(alias, command);
            // open target file
            File currentDir = new File(System.getProperty("user.dir"));
            File filePath = new File(currentDir.toString() + "\\src\\main\\data\\ParserPreferences.txt");

            PrintWriter writer = new PrintWriter(filePath);
            for (Map.Entry<String, String> entry : commandAliasMap.entrySet()) {
                writer.write(entry.getKey() + "|" + entry.getValue() + '\n');
            }
            writer.close();
            String successMessage = String.format("Noted. The alias %s has been removed\n", alias);
            ui.setMessage(successMessage);
        } catch (IOException | OptixException e) {
            ui.setMessage(e.getMessage());
        }
        return "";
    }
}
