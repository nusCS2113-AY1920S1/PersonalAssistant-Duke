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
    }

    /**
     * initialize Optix object.
     * path to the save file is defined in filePath.
     *
     * @param args is empty?
     */
    public static void main(String[] args) {
        File currentDir = new File(System.getProperty("user.dir"));
        File filePath = new File(currentDir.toString() + "\\src\\main\\data");
        new Optix(filePath).run();
    }

    /**
     * To boot up the software.
     */
    public void run() {
        boolean isExit = false;
        System.out.println(ui.showCommandLine());

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(model, ui, storage);
                isExit = c.isExit();
            } catch (OptixException e) {
                ui.setMessage(e.getMessage());
            } finally {
                System.out.println(ui.showCommandLine());
            }
        }
    }

    public String runGUI(String fullCommand) {
        String taskType = "";
        try {
            Command c = Parser.parse(fullCommand);
            taskType = c.execute(model, ui, storage);
        } catch (OptixException e) {
            ui.setMessage(e.getMessage());
        }
        return taskType;
    }

    public ShowMap getShows() {
        return model.getShows();
    }

    public String getResponse() {
        return ui.getMessage();
    }
}

