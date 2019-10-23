package optix.commands.parser;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixException;
import optix.ui.Ui;
import optix.util.Parser;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class AddAliasCommand extends Command {
    private String newAlias;
    private String command;
    private File preferenceFilePath;

    //@@ OungKennedy
    /**
     * Command to add a new alias to the command alias map.
     *
     * @param details String containing "NEW_ALIAS|COMMAND"
     */
    public AddAliasCommand(String details, File filePath) {
        String[] detailsArray = parseDetails(details);
        this.newAlias = detailsArray[0];
        this.command = detailsArray[1];
        this.preferenceFilePath = filePath;
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
        String message;
        // create parser object
        Parser dummyParser = new Parser(preferenceFilePath);
        try {
            // adds the alias-command pair to commandAliasMap, and saves it to file
            dummyParser.addAlias(newAlias, command);
            dummyParser.savePreferences();
            message = String.format("The new alias %s was successfully paired to the command %s\n", newAlias, command);
        } catch (OptixException | IOException e) {
            message = e.getMessage();
        }
        ui.setMessage(message);
        return "";
    }

    @Override
    public String[] parseDetails(String details) {
        return details.split("\\|", 2);
    }
}

