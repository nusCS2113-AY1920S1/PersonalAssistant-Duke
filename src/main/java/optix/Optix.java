package optix;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.commons.model.ShowMap;
import optix.exceptions.OptixException;
import optix.ui.Ui;
import optix.util.Parser;

import java.io.File;


/**
 * Software that stores all the finance for the Opera Hall.
 */
public class Optix {
    private Model model;

    private Ui ui;

    private Storage storage;

    private Parser parser;

    /**
     * Set up the storage, ui, and list of shows.
     * Save data is loaded from storage.load()
     *
     * @param filePath is the path to the file which contains save data.
     */

    public Optix(File filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        model = new Model(storage);
        parser = new Parser(filePath);
    }

    /**
     * Processes user input command.
     *
     * @param fullCommand User input command.
     * @return String for the type of Model that command deals with. E.g Show, Seat and Alias.
     */
    public String runGui(String fullCommand) {
        String taskType = "";
        try {
            Command c = parser.parse(fullCommand);
            taskType = c.execute(model, ui, storage);
        } catch (OptixException e) {
            ui.setMessage(e.getMessage());
        }
        return taskType;
    }

    public ShowMap getShows() {
        return model.getShows();
    }

    public ShowMap getShowsGui() {
        return model.getShowsGui();
    }

    public void resetShows() {
        model.setShowsGui(model.getShows());
    }

    public void resetArchive() {
        model.setShowsGui(model.getShowsHistory());
    }

    public String getResponse() {
        return ui.getMessage();
    }
}

