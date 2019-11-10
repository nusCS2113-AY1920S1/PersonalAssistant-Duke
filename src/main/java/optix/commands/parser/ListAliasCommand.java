package optix.commands.parser;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import optix.util.Parser;

import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

//@@author OungKennedy
public class ListAliasCommand extends Command {
    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * initialise logger.
     */
    public ListAliasCommand() {
        initLogger();
    }

    /**
     * Iterates through all entries in the commandAliasMap of a parser object.
     * Prints every key (alias) and value (command) pair.
     * to show response by program in ui and store existing data in Storage.
     *
     * @param model   The data structure holding all the information.
     * @param ui      The User Interface that reads user input and response to user.
     * @param storage The filepath of txt file which data are being stored.
     */
    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        OPTIXLOGGER.log(Level.INFO, "executing command");
        StringBuilder systemMessage = new StringBuilder("Alias list: \n");
        for (Map.Entry<String, String> entry : Parser.commandAliasMap.entrySet()) {
            systemMessage.append(entry.getKey()).append(" : ").append(entry.getValue()).append('\n');
        }
        ui.setMessage(systemMessage.toString());
        return "";
    }

    @Override
    public String[] parseDetails(String details) {
        return new String[0];
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

