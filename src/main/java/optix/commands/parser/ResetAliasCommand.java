package optix.commands.parser;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import optix.util.Parser;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

//@@author OungKennedy
public class ResetAliasCommand extends Command {
    private File preferenceFilePath;
    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ResetAliasCommand(File filePath) {
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
        OPTIXLOGGER.log(Level.INFO, "executing command");
        // open target file
        Parser dummyParser = new Parser(this.preferenceFilePath);
        // reset commandAliasMap in Parser class
        Parser.resetPreferences();
        // write the current contents of the commandAliasMap to the saveFile.
        String systemMessage;
        try {
            dummyParser.savePreferences();
            systemMessage = "Alias settings have been reset to default.\n";

        } catch (IOException e) {
            OPTIXLOGGER.log(Level.WARNING, "Error saving preferences.");
            systemMessage = e.getMessage();
        }
        ui.setMessage(systemMessage);
        return "";
    }

    /**
     * Dummy command.
     *
     * @param details n.a
     * @return n.a
     */
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

