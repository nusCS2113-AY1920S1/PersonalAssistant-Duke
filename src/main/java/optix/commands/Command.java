package optix.commands;

import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Command {
    public final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);//CHECKSTYLE IGNORE THIS LINE

    /**
     * Processes user input to be stored, queried, modified in ShowMap,
     * to show response by program in ui and store existing data in Storage.
     *
     * @param model   The data structure holding all the information.
     * @param ui      The User Interface that reads user input and response to user.
     * @param storage The filepath of txt file which data are being stored.
     */
    public abstract String execute(Model model, Ui ui, Storage storage);

    /**
     * Parses user input into its respective parameters.
     *
     * @param details User input command.
     * @return Array of string with respective parameters
     * @throws OptixInvalidCommandException The size of String array is not equals to expected
     *                                      number of parameters for the Command.
     */
    public abstract String[] parseDetails(String details) throws OptixInvalidCommandException;

    /**
     * Initialise logger.
     */
    public void initLogger() {
        // add a handler if there is no handler in the logger
        if (OPTIXLOGGER.getHandlers().length == 0) {
            OPTIXLOGGER.setLevel(Level.ALL);
            try {
                FileHandler fh = new FileHandler("OptixLogger.log");
                fh.setLevel(Level.FINE);
                OPTIXLOGGER.addHandler(fh);
            } catch (IOException e) {
                OPTIXLOGGER.log(Level.SEVERE, "File logger not working", e);
            }
        }
        OPTIXLOGGER.log(Level.FINEST, "Logging in " + this.getClass().getName());
    }
}
