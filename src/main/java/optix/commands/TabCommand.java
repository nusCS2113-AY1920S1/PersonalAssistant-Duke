package optix.commands;

import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TabCommand extends Command {
    String commandWord;

    private static final String MESSAGE_ARCHIVE = "Here is your list of archived shows.\n";
    private static final String MESSAGE_SHOW = "Here is your list of scheduled shows.\n";
    private static final String MESSAGE_FINANCE = "Here is your list of projected earnings.\n";
    private static final String MESSAGE_HELP = "Here are the list of commands you can use.\n";
    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public TabCommand(String commandWord) {
        this.commandWord = commandWord.trim().toLowerCase();
        initLogger();
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        OPTIXLOGGER.log(Level.INFO, "executing command");
        try {
            switch (commandWord) {
            case "archive":
                OPTIXLOGGER.log(Level.INFO, "archive case");
                model.setShowsGui(model.getShowsHistory());
                ui.setMessage(MESSAGE_ARCHIVE);
                break;
            case "show":
                OPTIXLOGGER.log(Level.INFO, "show case");
                model.setShowsGui(model.getShows());
                ui.setMessage(MESSAGE_SHOW);
                break;
            case "finance":
                OPTIXLOGGER.log(Level.INFO, "finance case");
                model.setShowsGui(model.getShows());
                ui.setMessage(MESSAGE_FINANCE);
                break;
            case "help":
                OPTIXLOGGER.log(Level.INFO, "help case");
                ui.setMessage(MESSAGE_HELP);
                break;
            default:
                OPTIXLOGGER.log(Level.WARNING, "TabCommand Error. commandWord:" + this.commandWord);
                throw new OptixInvalidCommandException();
            }
        } catch (OptixInvalidCommandException e) {
            OPTIXLOGGER.log(Level.WARNING, "Invalid input:" + this.commandWord);
            ui.setMessage(e.getMessage());
        }
        return commandWord;
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
