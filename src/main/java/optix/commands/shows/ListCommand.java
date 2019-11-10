package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.commons.model.ShowMap;
import optix.ui.Ui;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

//@@author CheeSengg
public class ListCommand extends Command {
    private static final String MESSAGE_LIST_FOUND = "Here are the list of shows:\n";

    private static final String MESSAGE_LIST_NOT_FOUND = "☹ OOPS!!! There are no shows in the near future.\n";
    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        ShowMap shows = model.getShows();
        StringBuilder message = new StringBuilder();

        if (!shows.isEmpty()) {
            message.append(MESSAGE_LIST_FOUND);
            message.append(model.listShow());
        } else {
            message = new StringBuilder(MESSAGE_LIST_NOT_FOUND);
        }
        ui.setMessage(message.toString());
        return "show";
    }

    @Override
    public String[] parseDetails(String details) {
        return new String[0];
    }

    private void initLogger() {
        LogManager.getLogManager().reset();
        OPTIXLOGGER.setLevel(Level.ALL);
        try {
            FileHandler fh = new FileHandler("OptixLogger.log");
            fh.setLevel(Level.FINE);
            OPTIXLOGGER.addHandler(fh);
        } catch (IOException e) {
            OPTIXLOGGER.log(Level.SEVERE, "File logger not working", e);
        }
        OPTIXLOGGER.log(Level.FINEST, "Logging in " + this.getClass().getName());
    }
}
