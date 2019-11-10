package optix.commands.parser;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;
import optix.util.Parser;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

//@@author OungKennedy
public class AddAliasCommand extends Command {
    private String details;
    private File preferenceFilePath;
    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final String MESSAGE_NOT_ACCEPTED = "☹ OOPS!!! Spaces are not allowed for alias command.\n"
                                                       + "Please try again";

    /**
     * Command to add a new alias to the command alias map.
     *
     * @param details String containing "NEW_ALIAS|COMMAND"
     */
    public AddAliasCommand(String details, File filePath) {
        this.details = details.trim();
        this.preferenceFilePath = filePath;
        initLogger();
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
        // parse the new alias and command from the details
        String newAlias;
        String command;
        try {
            String[] detailsArray = parseDetails(this.details);
            newAlias = detailsArray[0].trim();
            command = detailsArray[1].trim();
        } catch (OptixInvalidCommandException e) {
            OPTIXLOGGER.log(Level.WARNING, "error parsing details: " + this.details);
            ui.setMessage(e.getMessage());
            return "";
        }

        String message;
        Parser dummyParser = new Parser(preferenceFilePath);         // create parser object
        try {
            String[] aliasArray = newAlias.split(" ");
            if (aliasArray.length > 1 || aliasArray[0].equals("")) {
                throw new OptixException(MESSAGE_NOT_ACCEPTED);
            }
            // adds the alias-command pair to commandAliasMap, and saves it to file
            dummyParser.addAlias(newAlias, command);
            dummyParser.savePreferences();
            message = String.format("The new alias %s was successfully paired to the command %s\n", newAlias, command);
        } catch (OptixException | IOException e) {
            OPTIXLOGGER.log(Level.WARNING, String.format("error adding or saving alias. Alias:%s Command:%s", newAlias, command));
            message = e.getMessage();
        }
        ui.setMessage(message);
        return "";
    }

    @Override
    public String[] parseDetails(String details) throws OptixInvalidCommandException {
        String[] detailsArray = details.split("\\|", 2);
        if (detailsArray.length != 2) {
            throw new OptixInvalidCommandException();
        }
        return detailsArray;
    }

    private void initLogger() {
        LogManager.getLogManager().reset();
        OPTIXLOGGER.setLevel(Level.ALL);
        try {
            FileHandler fh = new FileHandler("OptixLogger.log", true);
            fh.setLevel(Level.FINE);
            OPTIXLOGGER.addHandler(fh);
        } catch (IOException e) {
            OPTIXLOGGER.log(Level.SEVERE, "File logger not working", e);
        }
        OPTIXLOGGER.log(Level.FINEST, "Logging in " + this.getClass().getName());
    }
}

