import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
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
                Command c = parser.parse(fullCommand);
                c.execute(model, ui, storage);
                isExit = c.isExit();
            } catch (OptixException e) {
                ui.setMessage(e.getMessage());
            } finally {
                System.out.println(ui.showCommandLine());
            }
        }

    }
}

